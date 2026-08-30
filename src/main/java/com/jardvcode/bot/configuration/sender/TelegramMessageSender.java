package com.jardvcode.bot.configuration.sender;

import com.jardvcode.bot.configuration.telegrambot.TelegramBotConfiguration;
import com.jardvcode.bot.shared.domain.bot.MessageAction;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

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

	public void sendActionMessage(String to, String message, List<MessageAction> actions) throws Exception {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(to);
		sendMessage.setText(message);

		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

		for (MessageAction action: actions) {
			List<InlineKeyboardButton> row = new ArrayList<>();

			InlineKeyboardButton button = new InlineKeyboardButton(action.label());
			button.setCallbackData(action.value());
			row.add(button);

			keyboard.add(row);
		}

		markup.setKeyboard(keyboard);
		sendMessage.setReplyMarkup(markup);

		chabot.execute(sendMessage);
	}

}
