package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.domain.AssignmentStatusEmoji;
import com.jardvcode.bot.checklist.domain.Emoji;
import com.jardvcode.bot.checklist.dto.AssignmentDTO;
import com.jardvcode.bot.checklist.dto.SectionDTO;
import com.jardvcode.bot.checklist.dto.ItemDTO;
import com.jardvcode.bot.checklist.entity.ItemViewEntity;
import com.jardvcode.bot.checklist.entity.ResponseEntity;
import com.jardvcode.bot.checklist.service.ResponseService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SelectItemState implements State {

    private final BotSessionDataService sessionDataService;
    private final ResponseService responseService;

    public SelectItemState(BotSessionDataService sessionDataService, ResponseService itemService) {
        this.sessionDataService = sessionDataService;
        this.responseService = itemService;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        SectionDTO sectionDTO = sessionDataService.findByBotUserId(botContext.getSystemUserId(), SectionDTO.class);
        AssignmentDTO assignmentDTO = sectionDTO.assignmentDTO();

        List<ResponseEntity> responses = responseService.findByAssignmentIdAndSectionId(assignmentDTO.assignmentId(), sectionDTO.id());

        StringBuilder message = new StringBuilder();

        for (ResponseEntity response : responses) {
            ItemViewEntity item = response.getItem();

            String statusValue = response.getStatus();
            String userResponse = "";

            AssignmentStatusEmoji status;

            if (statusValue != null) {
                userResponse = statusValue.toUpperCase() + " " + response.getComment();
                status = AssignmentStatusEmoji.COMPLETADO;
            } else {
                status = AssignmentStatusEmoji.PENDIENTE;
            }

            message.append(String.format(
                    "%s %d. %s%n" +
                    "   %s%n%n",
                    status.emoji(),
                    response.optionNumber(),
                    item.getLabel(),
                    userResponse
            ));
        }

        StringBuilder header = new StringBuilder();
        header.append(String.format(
                "%s %s%n" +
                "%s Operador: %s%n" +
                "%s Fecha: %s%n" +
                "%s Grupo: %s%n" +
                "%s Envía el número del punto de inspección que deseas responder:%n%n",
                Emoji.CHECKLIST,
                assignmentDTO.name(),
                Emoji.PERSON,
                assignmentDTO.operatorName(),
                Emoji.DATE,
                assignmentDTO.date(),
                Emoji.GROUP,
                sectionDTO.name(),
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

            SectionDTO sectionDTO = sessionDataService.findByBotUserId(botContext.getSystemUserId(), SectionDTO.class);
            AssignmentDTO assignmentDTO = sectionDTO.assignmentDTO();

            response = responseService.findByAssignmentIdAndSectionIdAndOptionNumber(assignmentDTO.assignmentId(), sectionDTO.id(), optionNumber);
        } catch(Exception e) {
            botContext.sendText("Opción no valida");

            return Decision.stay();
        }

        ItemDTO itemDTO = new ItemDTO(response.getId(), response.getItem().getLabel());

        sessionDataService.save(botContext.getSystemUserId(), itemDTO, getClass());

        return Decision.moveTo(AnswerItemState.class);
    }
}
