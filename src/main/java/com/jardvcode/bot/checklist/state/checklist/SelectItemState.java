package com.jardvcode.bot.checklist.state.checklist;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.domain.Emoji;
import com.jardvcode.bot.checklist.dto.ChecklistDTO;
import com.jardvcode.bot.checklist.dto.GroupDTO;
import com.jardvcode.bot.checklist.dto.ItemDTO;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import com.jardvcode.bot.checklist.entity.template.ItemEntity;
import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import com.jardvcode.bot.checklist.service.InstanceGroupService;
import com.jardvcode.bot.checklist.service.ItemResponseService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SelectItemState implements State {

    private final BotSessionDataService sessionDataService;
    private final ItemResponseService itemService;
    private final InstanceGroupService groupService;

    public SelectItemState(BotSessionDataService sessionDataService, ItemResponseService itemService, InstanceGroupService groupService) {
        this.sessionDataService = sessionDataService;
        this.itemService = itemService;
        this.groupService = groupService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        GroupDTO groupDTO = sessionDataService.findByUserId(botContext.getSystemUserId(), GroupDTO.class);
        ChecklistDTO checklistDTO = groupDTO.checklistDTO();

        List<ResponseEntity> responses = itemService.findByInstanceIdAndGroupId(checklistDTO.instanceId(), groupDTO.id());
        boolean groupItemsDone = true;

        StringBuilder message = new StringBuilder();

        for (ResponseEntity response : responses) {
            ItemEntity item = response.getItem();

            String statusValue = response.getStatus();
            String userResponse = "";

            ChecklistStatusEmoji status;

            if (statusValue != null) {
                userResponse = statusValue.toUpperCase() + " " + response.getObservation();
                status = ChecklistStatusEmoji.COMPLETADO;
            } else {
                status = ChecklistStatusEmoji.PENDIENTE;
                groupItemsDone = false;
            }

            message.append(String.format(
                    "%s %d. %s%n" +
                    "   %s%n%n",
                    status.emoji(),
                    response.getOptionNumber(),
                    item.getDescription(),
                    userResponse
            ));
        }

        if(groupItemsDone) {
            InstanceGroupEntity group = InstanceGroupEntity.withCompletedStatus(checklistDTO.instanceId(), groupDTO.id());
            groupService.update(group);
        }

        StringBuilder header = new StringBuilder();
        header.append(String.format(
                "%s %s%n" +
                "%s Operador: %s%n" +
                "%s Fecha: %s%n" +
                "%s Grupo: %s%n" +
                "%s Envía el número del punto de inspección que deseas responder:%n%n",
                Emoji.CHECKLIST,
                checklistDTO.name(),
                Emoji.PERSON,
                checklistDTO.operatorName(),
                Emoji.DATE,
                checklistDTO.date(),
                Emoji.GROUP,
                groupDTO.name(),
                Emoji.INSPECT
        ));

        botContext.sendText(header.toString());
        botContext.sendText(message.toString());

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        ResponseEntity response = null;

        try {
            Long optionNumber = Long.valueOf(botContext.getMessage());

            GroupDTO groupDTO = sessionDataService.findByUserId(botContext.getSystemUserId(), GroupDTO.class);
            ChecklistDTO checklistDTO = groupDTO.checklistDTO();

            response = itemService.findByInstanceIdAndGroupIdAndOptionNumber(checklistDTO.instanceId(), groupDTO.id(), optionNumber);
        } catch(Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        ItemDTO itemDTO = new ItemDTO(response.getId(), response.getItem().getDescription());

        sessionDataService.save(botContext.getSystemUserId(), itemDTO, getClass());

        return Decision.go(AnswerItemState.class);
    }
}
