package com.jardvcode.bot.configuration.statemachine;

import com.jardvcode.bot.checklist.domain.BotCommand;
import com.jardvcode.bot.user.state.InputTokenState;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.user.entity.BotUserEntity;
import com.jardvcode.bot.user.repository.UserBotStateRepository;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.shared.domain.state.StateUtil;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

@Service
public final class StateMachine {

	private final UserBotStateRepository repository;
	private final StateRegistry stateRegistry;
	private final CommandRegistry commandRegistry;
	private final BotSessionDataService botSessionDataService;

	public StateMachine(UserBotStateRepository repository, StateRegistry stateRegistry, CommandRegistry commandRegistry, BotSessionDataService botSessionDataService) {
		this.repository = repository;
		this.stateRegistry = stateRegistry;
		this.commandRegistry = commandRegistry;
        this.botSessionDataService = botSessionDataService;
    }

	public void apply(BotContext botContext) throws Exception {
		String providerUserId = botContext.getProviderUserId();
		String message = botContext.getMessage();

		BotUserEntity botUser = repository.findWithRolesAndPermissionsByProviderUserId(providerUserId).orElse(null);

		if(botUser == null) {
			String initialState = StateUtil.uniqueName(InputTokenState.class);

			stateRegistry.find(initialState).onBotMessage(botContext);

			botUser = BotUserEntity.create(botContext.getPlatform(), providerUserId, initialState);
			repository.save(botUser);

			return;
		}

		botContext.setSystemUserId(botUser.getUserId());

		if(botUser.getUserId() != null && message.contains(BotCommand.ASSIGNMENTS.value())) {
			botSessionDataService.deleteByBotUserId(botUser.getUserId());
		}

		if(botUser.getUserId() != null && message.contains("/") && commandRegistry.canExecute(message, botUser.permissions())) {
			String initialState = commandRegistry.find(message);
			stateRegistry.find(initialState).onBotMessage(botContext);

			botUser.setCurrentState(initialState);
			repository.save(botUser);
			
			return;
		}

		String currentState = botUser.getCurrentState();

		if(currentState == null) {
			return;
		}

		State currentStateFound = stateRegistry.find(currentState);

		if(currentStateFound == null) {
			return;
		}
		
		Decision newDecision = currentStateFound.onUserInput(botContext);
		String nextState =  newDecision.nextState();
				
		if(nextState == null) {
			return;
		}
		
		stateRegistry.find(nextState).onBotMessage(botContext);

		botUser.setCurrentState(nextState);
		repository.updateCurrentStateByProviderUserId(botUser.getProviderUserId(), nextState);
	}
}
