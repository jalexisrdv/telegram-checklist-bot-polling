package com.jardvcode.bot.user.state;

import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import org.springframework.stereotype.Service;

@Service
public final class WelcomeState implements State {
    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        String message = """
                ¡Hola! 👋\s
                Bienvenido al bot de inspecciones. Todo lo que necesitas para tus inspecciones está aquí:\s
                
                📋 /listas – Mira tus listas de inspección\s
                👥 /grupos – Revisa los grupos de tu lista\s
                📄 /reportes – Genera reportes\s
                
                ¡Elige una opción y empecemos! 🚀
                """;

        botContext.sendText(message);

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        return Decision.moveTo(getClass());
    }
}
