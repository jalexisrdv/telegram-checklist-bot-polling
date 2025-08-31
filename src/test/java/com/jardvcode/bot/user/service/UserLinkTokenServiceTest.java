package com.jardvcode.bot.user.service;

import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.user.entity.BotUserEntity;
import com.jardvcode.bot.user.entity.BotUserEntityMother;
import com.jardvcode.bot.user.entity.UserLinkTokenEntity;
import com.jardvcode.bot.user.entity.UserLinkTokenEntityMother;
import com.jardvcode.bot.user.repository.UserBotStateRepository;
import com.jardvcode.bot.user.repository.UserLinkTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLinkTokenServiceTest {

    @Mock
    private UserLinkTokenRepository userLinkTokenRepository;

    @Mock
    private UserBotStateRepository userBotRepository;

    @InjectMocks
    private UserLinkTokenService service;

    @Test
    void shouldThrowExceptionWhenTokenIsNotFound() {
        when(userLinkTokenRepository.findByToken(any())).thenReturn(Optional.empty());

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkUser("token", "platformUserId");
        });

        assertEquals( "No se pudo encontrar el token de acceso.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsUsed() {
        when(userLinkTokenRepository.findByToken(any())).thenReturn(Optional.of(UserLinkTokenEntityMother.withUsedToken()));

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkUser("token", "platformUserId");
        });

        assertEquals( "El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpired() {
        when(userLinkTokenRepository.findByToken(any())).thenReturn(Optional.of(UserLinkTokenEntityMother.withExpiredToken()));

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkUser("token", "platformUserId");
        });

        assertEquals( "El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        when(userLinkTokenRepository.findByToken(any())).thenReturn(Optional.of(UserLinkTokenEntityMother.withValidToken()));
        when(userBotRepository.findByPlatformUserId(any())).thenReturn(Optional.empty());

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkUser("token", "platformUserId");
        });

        assertEquals( "Usuario no encontrado.", exception.getMessage());
    }

    @Test
    void shouldLinkUserWhenTokenIsValid() {
        UserLinkTokenEntity userLinkTokenEntity = UserLinkTokenEntityMother.withValidToken();
        BotUserEntity botUserEntity = BotUserEntityMother.create();

        ArgumentCaptor<UserLinkTokenEntity> userLinkTokenEntityCaptor = ArgumentCaptor.forClass(UserLinkTokenEntity.class);
        ArgumentCaptor<BotUserEntity> botUserEntityCaptor = ArgumentCaptor.forClass(BotUserEntity.class);

        when(userLinkTokenRepository.findByToken(any())).thenReturn(Optional.of(userLinkTokenEntity));
        when(userBotRepository.findByPlatformUserId(any())).thenReturn(Optional.of(botUserEntity));

        service.linkUser("token", "platformUserId");

        verify(userLinkTokenRepository, times(1)).save(userLinkTokenEntityCaptor.capture());
        verify(userBotRepository, times(1)).save(botUserEntityCaptor.capture());

        userLinkTokenEntity = userLinkTokenEntityCaptor.getValue();
        botUserEntity = botUserEntityCaptor.getValue();

        assertEquals(true, userLinkTokenEntity.getUsed());
        assertEquals(userLinkTokenEntity.getUserId(), botUserEntity.getUserId());
    }

}