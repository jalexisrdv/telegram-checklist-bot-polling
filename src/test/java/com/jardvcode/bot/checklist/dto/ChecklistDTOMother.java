package com.jardvcode.bot.checklist.dto;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;

public final class ChecklistDTOMother {

    public static ChecklistDTO withInstance(InstanceEntity instance) {
        return new ChecklistDTO(
                instance.getId(),
                instance.getTemplate().getId(),
                instance.getTemplate().getName(),
                instance.getDate().toString(),
                instance.getOperatorName(),
                instance.getMileage(),
                instance.getNextService()
        );
    }

    public static ChecklistDTO create() {
        return new ChecklistDTO(
                1L,
                1L,
                "Formato para servicios A y C (BASICO)",
                "2025-09-14",
                "PEDRO OCELOT",
                "1299961",
                "1,300,000 BASICO"
        );
    }

}
