package com.jardvcode.bot.checklist.domain;

import com.jardvcode.bot.checklist.domain.overview.AssignmentOverview;
import com.jardvcode.bot.checklist.domain.overview.ProgressOverview;
import com.jardvcode.bot.checklist.domain.overview.SectionOverview;

import java.time.LocalDate;
import java.util.ArrayList;

public final class AssignmentOverviewMother {

    public static AssignmentOverview create() {
        ArrayList<SectionOverview> sections = new ArrayList<>();

        sections.add(new SectionOverview("Sistema de dirección", 0, 1));
        sections.add(new SectionOverview("Suspensión delantera", 0, 1));
        sections.add(new SectionOverview("Motor", 5, 6));
        sections.add(new SectionOverview("Embrague", 0, 2));
        sections.add(new SectionOverview("Transmisión", 3, 3));
        sections.add(new SectionOverview("Diferenciales", 0, 2));
        sections.add(new SectionOverview("Quintarueda", 0, 2));
        sections.add(new SectionOverview("Sistema eléctrico/electrónico", 3, 5));
        sections.add(new SectionOverview("Sistema de rodamiento", 0, 2));
        sections.add(new SectionOverview("Sistema de frenos", 1, 3));
        sections.add(new SectionOverview("Equipo de seguridad", 2, 2));
        sections.add(new SectionOverview("Limpieza y lubricación", 2, 2));

        ProgressOverview progress = new ProgressOverview(16, 31);

        return new AssignmentOverview(
                "Formato para servicio D(COMPLETO)",
                "279",
                "José Alexis Ramírez del Valle",
                "Juan Daniel Pérez Acosta",
                LocalDate.parse("2026-08-27"),
                sections,
                progress
        );
    }

}
