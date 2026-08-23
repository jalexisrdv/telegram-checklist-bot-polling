package com.jardvcode.bot.checklist.dto;

import com.jardvcode.bot.checklist.entity.AssignmentViewEntity;

import java.time.LocalDate;

public final class AssignmentDTOMother {

    public static AssignmentDTO withInstance(AssignmentViewEntity assignment) {
        return new AssignmentDTO(
                assignment.getId(),
                assignment.getTemplateId(),
                assignment.getTemplateName(),
                assignment.getDate().toString(),
                assignment.getOperatorFullName(),
                assignment.getMileage(),
                assignment.getNextService()
        );
    }

    public static AssignmentDTO create() {
        return new AssignmentDTO(
                1L,
                1L,
                "Formato para servicios A y C (BASICO)",
                LocalDate.now().toString(),
                "PEDRO OCELOT",
                "1299961",
                "1,300,000 BASICO"
        );
    }

}
