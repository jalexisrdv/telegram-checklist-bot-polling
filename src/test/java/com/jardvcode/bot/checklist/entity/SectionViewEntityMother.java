package com.jardvcode.bot.checklist.entity;

import com.jardvcode.bot.checklist.domain.AssignmentStatusEmoji;

import java.util.ArrayList;
import java.util.List;

public final class SectionViewEntityMother {

    public static List<SectionViewEntity> withCompletedStatus() {
        List<SectionViewEntity> sections = values();

        for (SectionViewEntity section: sections) {
            section.setStatus(AssignmentStatusEmoji.COMPLETADO.name());
        }

        return sections;
    }

    public static List<SectionViewEntity> withRandomStatus() {
        List<SectionViewEntity> sections = values();

        for (SectionViewEntity section: sections) {
            String status = section.getOptionNumber() % 2 == 0 ? AssignmentStatusEmoji.PENDIENTE.name() : AssignmentStatusEmoji.COMPLETADO.name();
            section.setStatus(status);
        }

        return sections;
    }

    public static SectionViewEntity withPendingGroup() {
        SectionViewEntity section = new SectionViewEntity();

        section.setId(1L);
        section.setAssignmentId(1L);
        section.setSectionId(1L);
        section.setName("SISTEMA DE DIRECCION");
        section.setOptionNumber(1);
        section.setStatus(AssignmentStatusEmoji.PENDIENTE.name());

        return section;
    }

    public static List<SectionViewEntity> values() {
        List<SectionViewEntity> sections = new ArrayList<>();

        sections.add(create(1L, "SISTEMA DE DIRECCION", 1));
        sections.add(create(2L, "SUSPENCION DELANTERA", 2));
        sections.add(create(3L, "MOTOR", 3));
        sections.add(create(4L, "EMBRAGUE", 4));
        sections.add(create(5L, "TRANSMISION", 5));
        sections.add(create(6L, "DIFERENCIALES", 6));
        sections.add(create(7L, "QUINTARUEDA", 7));
        sections.add(create(8L, "SISTEMA ELECTRICO/ELECTRONICO", 8));
        sections.add(create(9L, "SISTEMA DE RODAMIENTO", 9));
        sections.add(create(10L, "SISTEMA DE FRENOS", 10));
        sections.add(create(11L, "EQUIPO DE SEGURIDAD", 11));
        sections.add(create(12L, "LIMPIEZA Y LUBRICACION", 12));

        return sections;
    }

    public static SectionViewEntity create() {
        SectionViewEntity section = new SectionViewEntity();

        section.setId(1L);
        section.setName("SISTEMA DE DIRECCION");
        section.setOptionNumber(1);

        return section;
    }

    public static SectionViewEntity create(Long id, String name, Integer optionNumber) {
        SectionViewEntity section = new SectionViewEntity();

        section.setId(id);
        section.setName(name);
        section.setOptionNumber(optionNumber);

        return section;
    }

}
