package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.domain.BotCommand;
import com.jardvcode.bot.checklist.domain.AssignmentStatusEmoji;
import com.jardvcode.bot.checklist.domain.Emoji;
import com.jardvcode.bot.checklist.dto.AssignmentDTO;
import com.jardvcode.bot.checklist.dto.SectionDTO;
import com.jardvcode.bot.checklist.entity.SectionViewEntity;
import com.jardvcode.bot.checklist.service.SectionService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SelectSectionState implements State {

    private final BotSessionDataService sessionDataService;
    private final SectionService sectionService;

    public SelectSectionState(SectionService sectionService, BotSessionDataService sessionDataService) {
        this.sectionService = sectionService;
        this.sessionDataService = sessionDataService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        AssignmentDTO assignmentDTO = null;

        try {
            assignmentDTO = sessionDataService.findByBotUserId(botContext.getSystemUserId(), AssignmentDTO.class);
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

        List<SectionViewEntity> sections = sectionService.findByAssignmentId(assignmentDTO.assignmentId());

        for (SectionViewEntity section : sections) {
            String statusEmoji = AssignmentStatusEmoji.fromStatus(section.getStatus());

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
        AssignmentDTO assignmentDTO = null;
        SectionViewEntity section = null;

        try {
            Long optionNumber = Long.parseLong(botContext.getMessage());

            assignmentDTO = sessionDataService.findByBotUserId(botContext.getSystemUserId(), AssignmentDTO.class);

            section = sectionService.findByAssignmentIdAndOptionNumber(assignmentDTO.assignmentId(), optionNumber);
        } catch (Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        SectionDTO sectionDTO = new SectionDTO(section.getId(), section.getName(), assignmentDTO);

        sessionDataService.save(botContext.getSystemUserId(), sectionDTO, getClass());

        return Decision.moveTo(SelectItemState.class);
    }
}
