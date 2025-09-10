package com.jardvcode.bot.configuration.sender;

import java.util.List;
import java.util.Map;

public interface MessageSender {
	void sendText(String to, String message) throws Exception;
}
