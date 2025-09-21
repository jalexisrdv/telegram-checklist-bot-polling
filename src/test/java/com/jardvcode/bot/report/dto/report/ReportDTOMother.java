package com.jardvcode.bot.report.dto.report;

import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import com.jardvcode.bot.checklist.entity.instance.ResponseEntityMother;

import java.util.List;

public final class ReportDTOMother {

    public static ReportDTO create() {
        return withResponses(ResponseEntityMother.checklistResponses());
    }

    public static ReportDTO withResponses(List<ResponseEntity> responses) {
        return new ReportDTO(
                1L,
                HeaderDTOMother.create(),
                ResponseDTOMother.withResponses(responses)
        );
    }

}
