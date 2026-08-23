package com.jardvcode.bot.user.service;

import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.user.entity.BotUserEntity;
import com.jardvcode.bot.user.entity.BotUserEntityMother;
import com.jardvcode.bot.user.entity.BotActivationTokenEntity;
import com.jardvcode.bot.user.entity.BotActivationTokenEntityMother;
import com.jardvcode.bot.user.repository.BotUserStateRepository;
import com.jardvcode.bot.user.repository.BotActivationTokenRepository;
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
class BotErpUserLinkServiceTest {

    @Mock
    private BotActivationTokenRepository botActivationTokenRepository;

    @Mock
    private BotUserStateRepository botUserRepository;

    @InjectMocks
    private BotErpUserLinkService service;

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
        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.of(BotActivationTokenEntityMother.withUsedToken()));

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkBotToErpUser("token", "platformUserId");
        });

        assertEquals( "El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpired() {
        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.of(BotActivationTokenEntityMother.withExpiredToken()));

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkBotToErpUser("token", "platformUserId");
        });

        assertEquals( "El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        when(botActivationTokenRepository.findByToken(any())).thenReturn(Optional.of(BotActivationTokenEntityMother.withValidToken()));
        when(botUserRepository.findByProviderUserId(any())).thenReturn(Optional.empty());

        BotException exception = assertThrows(BotException.class, () -> {
            service.linkBotToErpUser("token", "platformUserId");
        });

        assertEquals( "Usuario no encontrado.", exception.getMessage());
    }

    @Test
    void shouldlinkBotToErpUserWhenTokenIsValid() {
        BotActivationTokenEntity botActivationToken = BotActivationTokenEntityMother.withValidToken();
        BotUserEntity botUser = BotUserEntityMother.create();

        ArgumentCaptor<BotActivationTokenEntity> botActivationTokenCaptor = ArgumentCaptor.forClass(BotActivationTokenEntity.class);
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