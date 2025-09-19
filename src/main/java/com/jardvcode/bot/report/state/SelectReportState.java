package com.jardvcode.bot.report.state;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.service.InstanceService;
import com.jardvcode.bot.report.dto.ChecklistDTO;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SelectReportState implements State {

    private final BotSessionDataService sessionDataService;
    private final InstanceService instanceService;

    public SelectReportState(BotSessionDataService sessionDataService, InstanceService instanceService) {
        this.sessionDataService = sessionDataService;
        this.instanceService = instanceService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        List<InstanceEntity> instances = instanceService.findUnconfirmedByUserId(botContext.getSystemUserId());

        if(instances.isEmpty()) {
            botContext.sendText("Aún no tienes listas de inspección asignadas, por lo que no es posible generar un reporte.");

            return Decision.stay();
        }

        StringBuilder message = new StringBuilder();
        message.append("Estas son tus listas de inspección asignadas. Envía el número de la lista que prefieras y generaré un reporte con tus respuestas actuales.\n\n");

        for (InstanceEntity instance : instances) {
            String statusEmoji = ChecklistStatusEmoji.fromStatus(instance.getStatus());

            message.append(String.format(
                    "%s %d. %s%n" +
                            "   - Unidad: %s%n" +
                            "   - Operador: %s%n" +
                            "   - Fecha: %s%n%n",
                    statusEmoji,
                    instance.getOptionNumber(),
                    instance.getTemplate().getName(),
                    instance.getUnitNumber(),
                    instance.getOperatorName(),
                    instance.getDate()
            ));

        }

        botContext.sendText(message.toString());

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        InstanceEntity instance = null;

        try {
            Long userId = botContext.getSystemUserId();
            Long optionNumber = Long.parseLong(botContext.getMessage());

            instance = instanceService.findByUserIdAndOptionNumber(userId, optionNumber);
        } catch (Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        ChecklistDTO checklistDTO = new ChecklistDTO(instance.getId(), instance.getUnitNumber());

        sessionDataService.save(botContext.getSystemUserId(), checklistDTO, getClass());

        return Decision.moveTo(GenerateReportState.class);
    }
}
