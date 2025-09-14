package com.jardvcode.bot.checklist.dto;

import com.jardvcode.bot.checklist.entity.InstanceEntityMother;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;

import java.time.LocalDate;

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

}
