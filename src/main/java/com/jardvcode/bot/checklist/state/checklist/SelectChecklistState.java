package com.jardvcode.bot.checklist.state.checklist;

import com.jardvcode.bot.checklist.domain.BotSessionData;
import com.jardvcode.bot.checklist.domain.ChecklistStatus;
import com.jardvcode.bot.checklist.dto.ChecklistDTO;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.service.InstanceService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.shared.util.JsonUtils;
import com.jardvcode.bot.user.entity.BotSessionDataEntity;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SelectChecklistState implements State {

    private final BotSessionDataService sessionDataService;
    private final InstanceService instanceService;

    public SelectChecklistState(BotSessionDataService sessionDataService, InstanceService instanceService) {
        this.sessionDataService = sessionDataService;
        this.instanceService = instanceService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        List<InstanceEntity> instances = instanceService.findUnconfirmedByUserId(1L);

        if(instances.isEmpty()) {
            botContext.sendText("¡Genial! No hay listas de inspección pendientes por responder.");

            return Decision.stay();
        }

        StringBuilder message = new StringBuilder();
        message.append("Estas son tus listas de inspección pendientes, envía el número de la lista que deseas responder:\n\n");

        for (InstanceEntity instance : instances) {
            String statusEmoji = ChecklistStatus.fromStatus(instance.getStatus());

            message.append(String.format(
                    "%s %d. %s%n" +
                            "   - Operador: %s%n" +
                            "   - Kilometraje: %s%n" +
                            "   - Próximo Servicio: %s%n" +
                            "   - Fecha: %s%n%n",
                    statusEmoji,
                    instance.getOptionNumber(),
                    instance.getTemplate().getName(),
                    instance.getOperatorName(),
                    instance.getMileage(),
                    instance.getNextService(),
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
            Long userId = 1L;
            Long optionNumber = Long.parseLong(botContext.getMessage());

            instance = instanceService.findByUserIdAndOptionNumber(userId, optionNumber);
        } catch (Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        ChecklistDTO checklistDTO = new ChecklistDTO(
                instance.getId(), instance.getTemplate().getId(),
                instance.getTemplate().getName(), instance.getDate().toString(),
                instance.getOperatorName(), instance.getMileage(), instance.getNextService()
        );

        BotSessionDataEntity sessionData = BotSessionDataEntity.create(
                botContext.getUserId(),
                getClass(),
                BotSessionData.CHECKLIST,
                JsonUtils.encode(checklistDTO)
        );

        sessionDataService.save(sessionData);

        return Decision.go(SelectGroupState.class);
    }
}
