package com.jardvcode.bot.configuration.statemachine;

import com.jardvcode.bot.shared.domain.state.State;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StateRegistry {
	
	private final HashMap<String, State> states = new HashMap<>();
	
	public StateRegistry(Map<String, State> implementedStates) {
		load(implementedStates);
	}
	
	private void load(Map<String, State> implementedStates) {
		implementedStates.values().forEach((state) -> {
			states.put(state.uniqueName(), state); 
		});
	}
	
	public State find(String state) {
		return states.get(state);
	}

}
