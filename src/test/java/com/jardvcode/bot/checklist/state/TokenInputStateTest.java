package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.StateUtil;
import com.jardvcode.bot.user.service.UserLinkTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenInputStateTest {

    @Mock
    private BotContext botContext;

    @Mock
    private UserLinkTokenService service;

    @InjectMocks
    private TokenInputState state;

    @Test
    void shouldSendTokenRequestMessageAndStayInState() throws Exception {
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        Decision decision = state.onBotMessage(botContext);

        verify(botContext).sendText(messageCaptor.capture());
        assertNull(decision.nextState());
        assertEquals("¡Hola! Para conectarte con tu cuenta, necesito que me envíes el token que recibiste.", messageCaptor.getValue());
    }

    @Test
    void shouldSendErrorMessageAndRetryWhenTokenIsInvalid() throws Exception {
        String expectedErrorMessage = "Token invalido o esperado";

        doThrow(new BotException(expectedErrorMessage)).when(service).linkBotUserToSystemUser(any(), any());

        Decision decision = state.onUserInput(botContext);

        verify(botContext, times(1)).sendText(expectedErrorMessage);
        assertEquals(StateUtil.uniqueName(TokenInputState.class), decision.nextState());
    }

    @Test
    void shouldLinkBotUserToSystemUserAndGoToMenuStateWhenTokenIsValid() throws Exception {
        doNothing().when(service).linkBotUserToSystemUser(any(), any());

        Decision decision = state.onUserInput(botContext);

        verify(service, times(1)).linkBotUserToSystemUser(any(), any());
        assertEquals(StateUtil.uniqueName(MenuState.class), decision.nextState());
    }

}