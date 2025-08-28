package com.jardvcode.bot.shared.domain.bot;

import com.jardvcode.bot.configuration.sender.MessageSender;

import java.util.List;
import java.util.Map;

public final class BotContext {
	
	private final String name;
	private final String userId;
	private final String message;
	private final String username;
	private final MessageSender sender;
	
	public BotContext(String name, String chatId, String message, String username, MessageSender sender) {
		this.name = name;
		this.userId = chatId;
		this.message = message;
		this.username = username;
		this.sender = sender;
	}
	
	public void sendText(String message) throws Exception {
		sender.sendText(userId, message);
	}

	public String getName() {
		return name;
	}

	public String getUserId() {
		return userId;
	}

	public String getMessage() {
		return message;
	}

	public String getUsername() {
		return username;
	}

}
