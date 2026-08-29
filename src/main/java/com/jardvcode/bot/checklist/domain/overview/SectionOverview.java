package com.jardvcode.bot.checklist.domain.overview;

import com.jardvcode.bot.checklist.domain.AssignmentStatusEmoji;

public record SectionOverview(
        String name,
        int completed,
        int total
) {

    public AssignmentStatusEmoji status() {
        if (total > 0 && completed >= total) {
            return AssignmentStatusEmoji.COMPLETADO;
        }

        return AssignmentStatusEmoji.PENDIENTE;
    }

}
