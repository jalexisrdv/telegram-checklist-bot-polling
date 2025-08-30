package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.service.UserLinkTokenService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import org.springframework.stereotype.Service;

@Service
public final class TokenInputState implements State {

    private final UserLinkTokenService service;

    public TokenInputState(UserLinkTokenService service) {
        this.service = service;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        botContext.sendText("¡Hola! Para conectarte con tu cuenta, necesito que me envíes el token que recibiste. \uD83D\uDE0A");

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        String token = botContext.getMessage();

        try {
            service.linkUser(token, botContext.getUserId());
        } catch(BotException e) {
            botContext.sendText(e.getMessage());

            return Decision.go(getClass());
        }

        return Decision.go(StartState.class);
    }
}
