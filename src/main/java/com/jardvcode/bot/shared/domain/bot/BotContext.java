package com.jardvcode.bot.shared.domain.bot;

import com.jardvcode.bot.configuration.sender.MessageSender;

public final class BotContext {
	
	private Long systemUserId;
	private final String providerUserId;
	private final String message;
	private final MessageSender sender;
	
	public BotContext(String providerUserId, String message, MessageSender sender) {
		this.providerUserId = providerUserId;
		this.message = message;
		this.sender = sender;
	}
	
	public void sendText(String message) throws Exception {
		sender.sendText(providerUserId, message);
	}

	public void sendDocument(byte[] file, String fileName) throws Exception {
		sender.sendDocument(providerUserId, file, fileName);
	}

	public Long getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(Long systemUserId) {
		this.systemUserId = systemUserId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public String getMessage() {
		return message;
	}

}
