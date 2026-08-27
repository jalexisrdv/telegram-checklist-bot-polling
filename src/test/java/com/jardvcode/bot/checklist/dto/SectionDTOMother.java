package com.jardvcode.bot.checklist.dto;

public final class SectionDTOMother {

    public static SectionDTO create() {
        return new SectionDTO(
                1L,
                "Sistema de dirección",
                AssignmentDTOMother.create()
        );
    }

}
