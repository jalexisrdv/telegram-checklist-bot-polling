package com.jardvcode.bot.checklist.dto;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;

import java.time.LocalDate;

public final class ChecklistDTOMother {

    public static ChecklistDTO withInstance(InstanceEntity assignment) {
        return new ChecklistDTO(
                assignment.getId(),
                assignment.getTemplateId(),
                assignment.getTemplateName(),
                assignment.getDate().toString(),
                assignment.getOperatorFullName(),
                assignment.getMileage(),
                assignment.getNextService()
        );
    }

    public static ChecklistDTO create() {
        return new ChecklistDTO(
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
