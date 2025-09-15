package com.jardvcode.bot.checklist.dto;

public final class GroupDTOMother {

    public static GroupDTO create() {
        return new GroupDTO(
                1L,
                "SISTEMA DE DIRECCION",
                ChecklistDTOMother.create()
        );
    }

}
