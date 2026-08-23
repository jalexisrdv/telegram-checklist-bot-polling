package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.dto.ChecklistDTO;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.service.InstanceService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SelectChecklistState implements State {

    private final BotSessionDataService sessionDataService;
    private final InstanceService assignmentService;

    public SelectChecklistState(BotSessionDataService sessionDataService, InstanceService assignmentService) {
        this.sessionDataService = sessionDataService;
        this.assignmentService = assignmentService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        List<InstanceEntity> assignments = assignmentService.findUnconfirmedByMechanicUserId(botContext.getSystemUserId());

        if(assignments.isEmpty()) {
            botContext.sendText("¡Genial! No hay listas de inspección pendientes por responder.");

            return Decision.stay();
        }

        StringBuilder message = new StringBuilder();
        message.append("Estas son tus listas de inspección pendientes, envía el número de la lista que deseas responder:\n\n");

        for (InstanceEntity assignment : assignments) {
            String statusEmoji = ChecklistStatusEmoji.fromStatus(assignment.getStatus());

            message.append(String.format(
                    "%s %d. %s%n" +
                            "   - Operador: %s%n" +
                            "   - Kilometraje: %s%n" +
                            "   - Próximo Servicio: %s%n" +
                            "   - Fecha: %s%n%n",
                    statusEmoji,
                    assignment.getOptionNumber(),
                    assignment.getTemplateName(),
                    assignment.getOperatorFullName(),
                    assignment.getMileage(),
                    assignment.getNextService(),
                    assignment.getDate()
            ));

        }

        botContext.sendText(message.toString());

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        InstanceEntity assignment = null;

        try {
            Long userId = botContext.getSystemUserId();
            Long optionNumber = Long.parseLong(botContext.getMessage());

            assignment = assignmentService.findByMechanicUserIdAndOptionNumber(userId, optionNumber);
        } catch (Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        ChecklistDTO assignmentDTO = new ChecklistDTO(
                assignment.getId(), assignment.getTemplateId(),
                assignment.getTemplateName(), assignment.getDate().toString(),
                assignment.getOperatorFullName(), assignment.getMileage(), assignment.getNextService()
        );

        sessionDataService.save(botContext.getSystemUserId(), assignmentDTO, getClass());

        return Decision.moveTo(SelectGroupState.class);
    }
}
