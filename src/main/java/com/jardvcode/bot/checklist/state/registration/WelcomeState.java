package com.jardvcode.bot.checklist.state.registration;

import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import org.springframework.stereotype.Service;

@Service
public final class WelcomeState implements State {
    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        String message = """
                ¡Hola! 👋 \s
                Bienvenido al bot de inspecciones. Aquí puedes gestionar tus listas y grupos: \s
                
                📋 /listas - Ver tus listas de inspección \s
                👥 /grupos - Ver grupos de la lista seleccionada \s
                
                ¡Elige una opción y comencemos! 🚀
                """;

        botContext.sendText(message);

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        return Decision.moveTo(getClass());
    }
}
