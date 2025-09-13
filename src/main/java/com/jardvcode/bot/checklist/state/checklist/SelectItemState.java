package com.jardvcode.bot.checklist.state.checklist;

import com.jardvcode.bot.checklist.domain.BotSessionData;
import com.jardvcode.bot.checklist.domain.ChecklistStatus;
import com.jardvcode.bot.checklist.domain.Emojis;
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
import com.jardvcode.bot.shared.util.JsonUtils;
import com.jardvcode.bot.user.entity.BotSessionDataEntity;
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
        GroupDTO groupDTO = sessionDataService.findByPlatformUserId(botContext.getUserId(), BotSessionData.GROUP.name(), GroupDTO.class);
        ChecklistDTO checklistDTO = groupDTO.checklistDTO();

        List<ResponseEntity> responses = itemService.findByInstanceIdAndGroupId(checklistDTO.instanceId(), groupDTO.id());
        boolean groupItemsDone = true;

        StringBuilder message = new StringBuilder();

        for (ResponseEntity response : responses) {
            ItemEntity item = response.getItem();

            String statusValue = response.getStatus();
            String userResponse = "";

            ChecklistStatus status;

            if (statusValue != null) {
                userResponse = statusValue.toUpperCase() + " " + response.getObservation();
                status = ChecklistStatus.COMPLETADO;
            } else {
                status = ChecklistStatus.PENDIENTE;
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
                Emojis.CHECKLIST,
                checklistDTO.name(),
                Emojis.PERSON,
                checklistDTO.operatorName(),
                Emojis.DATE,
                checklistDTO.date(),
                Emojis.GROUP,
                groupDTO.name(),
                Emojis.INSPECT
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

            GroupDTO groupDTO = sessionDataService.findByPlatformUserId(botContext.getUserId(), BotSessionData.GROUP.name(), GroupDTO.class);
            ChecklistDTO checklistDTO = groupDTO.checklistDTO();

            response = itemService.findByInstanceIdAndGroupIdAndOptionNumber(checklistDTO.instanceId(), groupDTO.id(), optionNumber);
        } catch(Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        ItemDTO itemDTO = new ItemDTO(response.getId(), response.getItem().getDescription());

        BotSessionDataEntity sessionData = BotSessionDataEntity.create(
                botContext.getUserId(),
                getClass(),
                BotSessionData.ITEM,
                JsonUtils.encode(itemDTO)
        );

        sessionDataService.save(sessionData);

        return Decision.go(AnswerItemState.class);
    }
}
