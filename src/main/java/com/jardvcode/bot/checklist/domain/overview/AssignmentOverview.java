package com.jardvcode.bot.checklist.domain.overview;

import com.jardvcode.bot.checklist.domain.AssignmentStatusEmoji;

import java.time.LocalDate;
import java.util.List;

public class AssignmentOverview {

    private final String templateName;
    private final String unit;
    private final String operator;
    private final String mechanic;
    private final LocalDate date;
    private final List<SectionOverview> sections;
    private final ProgressOverview progress;

    public AssignmentOverview(
            String templateName,
            String unit,
            String operator,
            String mechanic,
            LocalDate date,
            List<SectionOverview> sections,
            ProgressOverview progress
    ) {
        this.templateName = templateName;
        this.unit = unit;
        this.operator = operator;
        this.mechanic = mechanic;
        this.date = date;
        this.sections = sections;
        this.progress = progress;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getUnit() {
        return unit;
    }

    public String getOperator() {
        return operator;
    }

    public String getMechanic() {
        return mechanic;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<SectionOverview> getSections() {
        return sections;
    }

    public int getCompleted() {
        return progress.getCompleted();
    }

    public int getTotal() {
        return progress.getTotal();
    }

    public int getPercentage() {
        return progress.percentage();
    }

    public AssignmentStatusEmoji status() {
        if (progress.getCompleted() == progress.getTotal()) {
            return AssignmentStatusEmoji.COMPLETADO;
        }

        return AssignmentStatusEmoji.PENDIENTE;
    }

}