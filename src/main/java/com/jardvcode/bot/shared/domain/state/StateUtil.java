package com.jardvcode.bot.shared.domain.state;

public class StateUtil {
	
	private StateUtil() {
		
	}
	
	public static String uniqueName(Class<? extends State> state) {
		if(state == null) {
			return null;
		}
		
		return state.getCanonicalName();
	}

}
