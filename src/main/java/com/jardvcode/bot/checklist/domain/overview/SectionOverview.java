package com.jardvcode.bot.checklist.domain.overview;

import com.jardvcode.bot.checklist.domain.StatusEnum;

public record SectionOverview(
        String name,
        int completed,
        int total
) {

    public StatusEnum status() {
        if (total > 0 && completed >= total) {
            return StatusEnum.COMPLETED;
        }

        return StatusEnum.PENDING;
    }

}
