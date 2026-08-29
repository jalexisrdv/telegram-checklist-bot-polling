package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.domain.BotCommand;
import com.jardvcode.bot.checklist.domain.Emoji;
import com.jardvcode.bot.checklist.domain.overview.AssignmentOverview;
import com.jardvcode.bot.checklist.dto.AssignmentDTO;
import com.jardvcode.bot.checklist.service.AssignmentOverviewService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public final class AssignmentOverviewState implements State {

    private final BotSessionDataService sessionDataService;
    private final AssignmentOverviewService service;

    public AssignmentOverviewState(BotSessionDataService sessionDataService, AssignmentOverviewService service) {
        this.sessionDataService = sessionDataService;
        this.service = service;
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

        AssignmentOverview overview = service.getOverview(assignmentDTO.assignmentId());

        String message = String.format(
        """
        %s Lista de inspección: %s
        %s Estatus: %s
        %s Progreso: %d/%d verificaciones completadas (%d%%)
        
        %s Código de unidad: %s
        %s Operador: %s
        %s Mecánico: %s
        %s Fecha de asignación: %s
        
        %s Secciones
        
        %s
        """,
            Emoji.CHECKLIST, overview.getTemplateName(),
            overview.status().emoji(), StringUtils.capitalize(overview.status().toString().toLowerCase()),
            Emoji.PROGRESS_PERCENT, overview.getCompleted(), overview.getTotal(), overview.getPercentage(),

            Emoji.VEHICLE, overview.getUnit(),
            Emoji.DRIVER, overview.getOperator(),
            Emoji.MECHANIC, overview.getMechanic(),
            Emoji.DATE, overview.getDate(),
            Emoji.GROUP, overview.getSections().stream()
                    .map(section -> String.format(
                            "%s %s — %d/%d completados",
                            section.status().emoji(),
                            StringUtils.capitalize(section.name().toLowerCase()),
                            section.completed(),
                            section.total()
                    ))
                    .collect(Collectors.joining("\n"))
        );

        botContext.sendText(message);

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        return Decision.stay();
    }

}
