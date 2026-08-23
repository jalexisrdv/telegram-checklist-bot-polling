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
    private UserLinkTokenRepository botActivationTokenRepository;

    @Mock
    private UserBotStateRepository botUserRepository;

    @InjectMocks
    private UserLinkTokenService service;

    @Test
    void shouldThrowExceptionWhenTokenIsNotFound() {
        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.empty());

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkBotToErpUser("token", "platformUserId");
        });

        assertEquals( "No se pudo encontrar el token de acceso.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsUsed() {
        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.of(UserLinkTokenEntityMother.withUsedToken()));

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkBotToErpUser("token", "platformUserId");
        });

        assertEquals( "El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpired() {
        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.of(UserLinkTokenEntityMother.withExpiredToken()));

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkBotToErpUser("token", "platformUserId");
        });

        assertEquals( "El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.of(UserLinkTokenEntityMother.withValidToken()));
        when(botUserRepository.findByProviderUserId(any())).thenReturn(Optional.empty());

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkBotToErpUser("token", "platformUserId");
        });

        assertEquals( "Usuario no encontrado.", exception.getMessage());
    }

    @Test
    void shouldlinkBotToErpUserWhenTokenIsValid() {
        UserLinkTokenEntity botActivationToken = UserLinkTokenEntityMother.withValidToken();
        BotUserEntity botUser = BotUserEntityMother.create();

        ArgumentCaptor<UserLinkTokenEntity> botActivationTokenCaptor = ArgumentCaptor.forClass(UserLinkTokenEntity.class);
        ArgumentCaptor<BotUserEntity> botUserCaptor = ArgumentCaptor.forClass(BotUserEntity.class);

        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.of(botActivationToken));
        when(botUserRepository.findByProviderUserId(any())).thenReturn(Optional.of(botUser));

        service.linkBotToErpUser("token", "platformUserId");

        verify(botActivationTokenRepository, times(1)).save(botActivationTokenCaptor.capture());
        verify(botUserRepository, times(1)).save(botUserCaptor.capture());

        botActivationToken = botActivationTokenCaptor.getValue();
        botUser = botUserCaptor.getValue();

        assertNotNull(botActivationToken.getUsedAt());
        assertEquals(botActivationToken.getUserId(), botUser.getUserId());
    }

}