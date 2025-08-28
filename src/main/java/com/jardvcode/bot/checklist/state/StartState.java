package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import org.springframework.stereotype.Service;

@Service
public class StartState implements State {
    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        botContext.sendText("Hello!!!");

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        return Decision.go(getClass());
    }
}
