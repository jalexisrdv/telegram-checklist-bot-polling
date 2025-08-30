package com.jardvcode.bot.configuration.statemachine;

import com.jardvcode.bot.checklist.state.StartState;
import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.shared.domain.state.StateUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public final class CommandRegistry {

    private final HashMap<String, Command> commands;

    public CommandRegistry() {
        this.commands = load();
    }

    public boolean canExecute(String command, Set<String> permissions) {
        if(!commands.containsKey(command)) {
            return false;
        }

        if(!permissions.contains(commands.get(command).permission)) {
            throw new BotException("No tienes permisos para ejecutar comando " + command);
        }

        return true;
    }

    public String find(String command) {
        return commands.get(command).initialState();
    }

    private HashMap<String, Command> load() {
        HashMap<String, Command> commands = new HashMap<>();

        commands.put("/checklist", Command.create("COMPLETE_CHECKLIST", StartState.class));

        return commands;
    }

    private record Command(String permission, String initialState) {
        public static Command create(String permission, Class<? extends State> state) {
            return new Command(permission, StateUtil.uniqueName(state));
        }
    }

}
