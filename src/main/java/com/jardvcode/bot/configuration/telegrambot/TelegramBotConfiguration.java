package com.jardvcode.bot.configuration.telegrambot;

import com.jardvcode.bot.configuration.sender.MessageSender;
import com.jardvcode.bot.configuration.sender.TelegramMessageSender;
import com.jardvcode.bot.configuration.statemachine.StateMachine;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.exception.BotException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public final class TelegramBotConfiguration extends TelegramLongPollingBot {
	
	private final String username;
	private final StateMachine stateMachine;
	private final MessageSender messageSender;
	
	public TelegramBotConfiguration(StateMachine stateMachine, TelegramPropertiesConfiguration properties) {
		super(getDefaultBotOptions(properties.getApi()), properties.getToken());
		this.username = properties.getUsername();
		this.stateMachine = stateMachine;
		this.messageSender = new TelegramMessageSender(this);
	}
	
	@PostConstruct
	void init() throws TelegramApiException {
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
	}
	
	public static DefaultBotOptions getDefaultBotOptions(String baseUrl) {
		DefaultBotOptions options = new DefaultBotOptions();
        options.setBaseUrl(baseUrl);
        return options;
	}

	@Override
	public void onUpdateReceived(Update update) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String chatId = null;
				String incomingMessage = null;
				String username = null;
	
				if (update.hasCallbackQuery()) {
					chatId = update.getCallbackQuery().getMessage().getChatId().toString();
					incomingMessage = update.getCallbackQuery().getData();
					username = update.getCallbackQuery().getFrom().getFirstName();
				}
	
				if (update.hasMessage() && update.getMessage().hasText()) {
					chatId = update.getMessage().getChatId().toString();
					incomingMessage = update.getMessage().getText();
					username = update.getMessage().getChat().getFirstName();
				}
				
				if (chatId == null || incomingMessage == null || username == null) return;
				
				BotContext botContext = new BotContext(TelegramBotConfiguration.this.username, chatId, incomingMessage, username, messageSender);
				
				try {
					stateMachine.apply(botContext);
				} catch (BotException e) {
                    try {
                        botContext.sendText(e.getMessage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
                    try {
                        botContext.sendText("Oops... algo no salió como esperaba");
                    } catch (Exception ex) {
						ex.printStackTrace();
					}
                }
			}
		}).start();
		
	}

	@Override
	public String getBotUsername() {
		return username;
	}

}
