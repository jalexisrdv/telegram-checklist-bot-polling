package com.jardvcode.bot.configuration.statemachine;

import com.jardvcode.bot.checklist.state.StartState;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.entity.UserBotStateEntity;
import com.jardvcode.bot.shared.domain.repository.UserBotStateRepository;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.shared.domain.state.StateUtil;
import org.springframework.stereotype.Service;

@Service
public final class StateMachine {

	private final UserBotStateRepository repository;
	private final StateRegistry registry;

	public StateMachine(UserBotStateRepository repository, StateRegistry registry) {
		this.repository = repository;
		this.registry = registry;
	}

	public void apply(BotContext botContext) throws Exception {
		String userId = botContext.getUserId();
		String message = botContext.getMessage();

		UserBotStateEntity user = repository.findByPlatformUserId(userId)
				.orElse(UserBotStateEntity.create(userId, StateUtil.uniqueName(StartState.class)));
		
		if(message.equalsIgnoreCase("/start")) {
			String initialState = StateUtil.uniqueName(StartState.class);
			
			registry.find(initialState).onBotMessage(botContext);

			user.setCurrentState(initialState);
			repository.save(user);
			
			return;
		}

		String currentState = user.getCurrentState();

		if(currentState == null) {
			return;
		}

		State state = registry.find(currentState);

		if(state == null) {
			return;
		}
		
		Decision newDecision = state.onUserInput(botContext);
		String nextState =  newDecision.nextState();
				
		if(nextState == null) {
			return;
		}
		
		registry.find(nextState).onBotMessage(botContext);

		user.setCurrentState(nextState);
		repository.save(user);
	}
}
