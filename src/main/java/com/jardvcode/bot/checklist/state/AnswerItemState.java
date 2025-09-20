package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.dto.ItemDTO;
import com.jardvcode.bot.checklist.service.ItemResponseService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public final class AnswerItemState implements State {

    private final BotSessionDataService sessionDataService;
    private final ItemResponseService responseService;

    public AnswerItemState(BotSessionDataService sessionDataService, ItemResponseService responseService) {
        this.sessionDataService = sessionDataService;
        this.responseService = responseService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        ItemDTO itemDTO = sessionDataService.findByUserId(botContext.getSystemUserId(), ItemDTO.class);

        botContext.sendText("Envía el estatus de " + itemDTO.description() + ": ");

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        String response = botContext.getMessage();

        Pattern pattern = Pattern.compile("^\s*(R|F|OK)(\s(.*))?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(response);

        if (!matcher.matches()) {
            String message = """
                    Formato no válido. Responda únicamente con \"F\" (fallas), \"OK\" (en condiciones) o \"R\" (se reparó). Opcionalmente, agregue un espacio seguido de sus observaciones. Ejemplo: F MIS OBSERVACIONES
                    """;
            botContext.sendText(message);

            return Decision.moveTo(getClass());
        }

        String status = matcher.group(1).trim();
        String observation = Optional.ofNullable(matcher.group(2)).orElse("").trim();

        ItemDTO itemDTO = sessionDataService.findByUserId(botContext.getSystemUserId(), ItemDTO.class);

        responseService.save(itemDTO.id(), status, observation);

        return Decision.moveTo(SelectItemState.class);
    }
}
