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
    private final InstanceGroupService sectionService;

    public SelectGroupState(InstanceGroupService sectionService, BotSessionDataService sessionDataService) {
        this.sectionService = sectionService;
        this.sessionDataService = sessionDataService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        ChecklistDTO assignmentDTO = null;

        try {
            assignmentDTO = sessionDataService.findByBotUserId(botContext.getSystemUserId(), ChecklistDTO.class);
        } catch (Exception e) {
            botContext.sendText("Aún no has seleccionado una lista de inspección. Envía o pulsa " + BotCommand.ASSIGNMENTS.value() + " para ver las listas disponibles.");

            return Decision.stay();
        }

        StringBuilder message = new StringBuilder();

        message.append(String.format(
                "%s %s%n" +
                "   - Operador: %s%n" +
                "   - Fecha: %s%n%n" +
                "%s Envía el número del grupo para mostrar los puntos de inspección:%n%n",
                Emoji.CHECKLIST,
                assignmentDTO.name(),
                assignmentDTO.operatorName(),
                assignmentDTO.date(),
                Emoji.GROUP
        ));

        List<InstanceGroupEntity> sections = sectionService.findByAssignmentId(assignmentDTO.assignmentId());

        for (InstanceGroupEntity section : sections) {
            String statusEmoji = ChecklistStatusEmoji.fromStatus(section.getStatus());

            message.append(String.format(
                    "%s %d. %s%n",
                    statusEmoji,
                    section.getOptionNumber(),
                    section.getName()
            ));
        }

        botContext.sendText(message.toString());

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        ChecklistDTO assignmentDTO = null;
        InstanceGroupEntity section = null;

        try {
            Long optionNumber = Long.parseLong(botContext.getMessage());

            assignmentDTO = sessionDataService.findByBotUserId(botContext.getSystemUserId(), ChecklistDTO.class);

            section = sectionService.findByAssignmentIdAndOptionNumber(assignmentDTO.assignmentId(), optionNumber);
        } catch (Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        GroupDTO sectionDTO = new GroupDTO(section.getId(), section.getName(), assignmentDTO);

        sessionDataService.save(botContext.getSystemUserId(), sectionDTO, getClass());

        return Decision.moveTo(SelectItemState.class);
    }
}
