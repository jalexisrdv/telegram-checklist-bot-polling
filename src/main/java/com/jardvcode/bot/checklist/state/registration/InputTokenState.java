package com.jardvcode.bot.checklist.state.registration;

import com.jardvcode.bot.user.service.UserLinkTokenService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import org.springframework.stereotype.Service;

@Service
public final class InputTokenState implements State {

    private final UserLinkTokenService service;

    public InputTokenState(UserLinkTokenService service) {
        this.service = service;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        botContext.sendText("¡Hola! Para conectarte con tu cuenta, necesito que me envíes el token que recibiste.");

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        String token = botContext.getMessage();

        try {
            service.linkBotUserToSystemUser(token, botContext.getProviderUserId());
        } catch(BotException e) {
            botContext.sendText(e.getMessage());

            return Decision.moveTo(getClass());
        }

        return Decision.moveTo(WelcomeState.class);
    }
}
