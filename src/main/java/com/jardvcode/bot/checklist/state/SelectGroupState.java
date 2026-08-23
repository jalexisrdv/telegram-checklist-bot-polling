package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.domain.BotCommand;
import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.domain.Emoji;
import com.jardvcode.bot.checklist.dto.ChecklistDTO;
import com.jardvcode.bot.checklist.dto.GroupDTO;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import com.jardvcode.bot.checklist.service.InstanceGroupService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SelectGroupState implements State {

    private final BotSessionDataService sessionDataService;
    private final InstanceGroupService groupService;

    public SelectGroupState(InstanceGroupService groupService, BotSessionDataService sessionDataService) {
        this.groupService = groupService;
        this.sessionDataService = sessionDataService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        ChecklistDTO checklistDTO = null;

        try {
            checklistDTO = sessionDataService.findByUserId(botContext.getSystemUserId(), ChecklistDTO.class);
        } catch (Exception e) {
            botContext.sendText("Aún no has seleccionado una lista de inspección. Envía o pulsa " + BotCommand.CHECKLISTS.value() + " para ver las listas disponibles.");

            return Decision.stay();
        }

        StringBuilder message = new StringBuilder();

        message.append(String.format(
                "%s %s%n" +
                "   - Operador: %s%n" +
                "   - Fecha: %s%n%n" +
                "%s Envía el número del grupo para mostrar los puntos de inspección:%n%n",
                Emoji.CHECKLIST,
                checklistDTO.name(),
                checklistDTO.operatorName(),
                checklistDTO.date(),
                Emoji.GROUP
        ));

        List<InstanceGroupEntity> groups = groupService.findByInstanceId(checklistDTO.instanceId());

        for (InstanceGroupEntity group : groups) {
            String statusEmoji = ChecklistStatusEmoji.fromStatus(group.getStatus());

            message.append(String.format(
                    "%s %d. %s%n",
                    statusEmoji,
                    group.getOptionNumber(),
                    group.getName()
            ));
        }

        botContext.sendText(message.toString());

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        ChecklistDTO checklistDTO = null;
        InstanceGroupEntity group = null;

        try {
            Long optionNumber = Long.parseLong(botContext.getMessage());

            checklistDTO = sessionDataService.findByUserId(botContext.getSystemUserId(), ChecklistDTO.class);

            group = groupService.findByInstanceIdAndOptionNumber(checklistDTO.instanceId(), optionNumber);
        } catch (Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        GroupDTO groupDTO = new GroupDTO(group.getId(), group.getName(), checklistDTO);

        sessionDataService.save(botContext.getSystemUserId(), groupDTO, getClass());

        return Decision.moveTo(SelectItemState.class);
    }
}
