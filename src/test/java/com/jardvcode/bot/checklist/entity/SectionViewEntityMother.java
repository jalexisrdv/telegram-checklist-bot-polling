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
        section.setName("Sistema de dirección");
        section.setOptionNumber(1);
        section.setStatus(AssignmentStatusEmoji.PENDIENTE.name());

        return section;
    }

    public static List<SectionViewEntity> values() {
        List<SectionViewEntity> sections = new ArrayList<>();

        sections.add(create(1L, "Sistema de dirección", 1));
        sections.add(create(2L, "Suspensión delantera", 2));
        sections.add(create(3L, "Motor", 3));
        sections.add(create(4L, "Embrague", 4));
        sections.add(create(5L, "Transmisión", 5));
        sections.add(create(6L, "Diferenciales", 6));
        sections.add(create(7L, "Quintarueda", 7));
        sections.add(create(8L, "Sistema eléctrico/electrónico", 8));
        sections.add(create(9L, "Sistema de rodamiento", 9));
        sections.add(create(10L, "Sistema de frenos", 10));
        sections.add(create(11L, "Equipo de seguridad", 11));
        sections.add(create(12L, "Limpieza y lubricación", 12));

        return sections;
    }

    public static SectionViewEntity create() {
        SectionViewEntity section = new SectionViewEntity();

        section.setId(1L);
        section.setName("Sistema de dirección");
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
