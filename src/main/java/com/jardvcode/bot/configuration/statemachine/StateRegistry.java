package com.jardvcode.bot.configuration.statemachine;

import com.jardvcode.bot.shared.domain.state.State;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class StateRegistry {
	
	private final HashMap<Class<? extends State>, State> states = new HashMap<>();
	
	public StateRegistry(Map<String, State> implementedStates) {
		load(implementedStates);
	}
	
	private void load(Map<String, State> implementedStates) {
		implementedStates.values().forEach((state) -> {
			states.put(state.getClass(), state);
		});
	}
	
	public State find(Class<? extends State> state) {
		return states.get(state);
	}

}
