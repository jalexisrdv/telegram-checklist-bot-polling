package com.jardvcode.bot.configuration.sender;

import com.jardvcode.bot.shared.domain.bot.MessageAction;

import java.util.List;

public interface MessageSender {
	void sendText(String to, String message) throws Exception;
	void sendDocument(String to, byte[] file, String fileName) throws Exception;
	void sendActionMessage(String to, String message, List<MessageAction> actions) throws Exception;
}
