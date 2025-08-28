package com.jardvcode.bot.configuration.sender;

import com.jardvcode.bot.configuration.telegrambot.TelegramBotConfiguration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class TelegramMessageSender implements MessageSender {
	
	private final TelegramBotConfiguration chabot;
	
	public TelegramMessageSender(TelegramBotConfiguration chabot) {
		this.chabot = chabot;
	}
	
	public void sendText(String to, String message) throws Exception {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(to);
		sendMessage.setText(message);
		
		chabot.execute(sendMessage);
	}

}
