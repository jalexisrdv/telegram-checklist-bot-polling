package com.jardvcode.bot.configuration.sender;

import com.jardvcode.bot.configuration.telegrambot.TelegramBotConfiguration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.ByteArrayInputStream;

@Service
public final class TelegramMessageSender implements MessageSender {
	
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

	@Override
	public void sendDocument(String to, byte[] file, String fileName) throws Exception {
		SendDocument sendDocument = new SendDocument();
		sendDocument.setChatId(to);

		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file)) {
			sendDocument.setDocument(new InputFile(byteArrayInputStream, fileName));
			chabot.execute(sendDocument);
		}
	}

}
