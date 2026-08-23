package com.jardvcode.bot.shared.domain.state;

public final class Decision {
	
	private final Class<? extends State> state;

	private Decision(Class<? extends State> state) {
		this.state = state;
	}

	public Class<? extends State> nextState() {
		return state;
	}

	public static Decision stay() {
		return new Decision(null);
	}

	public static Decision moveTo(Class<? extends State> next) {
		return new Decision(next);
	}
	
}