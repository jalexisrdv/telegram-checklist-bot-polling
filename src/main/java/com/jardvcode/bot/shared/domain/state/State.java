package com.jardvcode.bot.shared.domain.state;

import com.jardvcode.bot.shared.domain.bot.BotContext;

public interface State {
	
	Decision onBotMessage(BotContext botContext) throws Exception;
	
	Decision onUserInput(BotContext botContext) throws Exception;

}
