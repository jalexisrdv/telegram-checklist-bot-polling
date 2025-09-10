package com.jardvcode.bot.configuration.statemachine;

import com.jardvcode.bot.checklist.domain.BotCommand;
import com.jardvcode.bot.checklist.state.registration.InputTokenState;
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
		String userId = botContext.getUserId();
		String message = botContext.getMessage();

		BotUserEntity user = repository.findByPlatformUserId(userId).orElse(null);

		if(user == null) {
			String initialState = StateUtil.uniqueName(InputTokenState.class);

			stateRegistry.find(initialState).onBotMessage(botContext);

			user = BotUserEntity.create(userId, initialState);
			repository.save(user);

			return;
		}

		if(message.contains(BotCommand.CHECKLISTS.value())) {
			botSessionDataService.deleteByPlatformUserId(botContext.getUserId());
		}

		if(user.getUserId() != null && message.contains("/") && commandRegistry.canExecute(message, user.permissions())) {
			String initialState = commandRegistry.find(message);
			stateRegistry.find(initialState).onBotMessage(botContext);

			user.setCurrentState(initialState);
			repository.save(user);
			
			return;
		}

		String currentState = user.getCurrentState();

		if(currentState == null) {
			return;
		}

		State state = stateRegistry.find(currentState);

		if(state == null) {
			return;
		}
		
		Decision newDecision = state.onUserInput(botContext);
		String nextState =  newDecision.nextState();
				
		if(nextState == null) {
			return;
		}
		
		stateRegistry.find(nextState).onBotMessage(botContext);

		user.setCurrentState(nextState);
		repository.updateCurrentStateByPlatformUserId(userId, nextState);
	}
}
