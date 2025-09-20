package com.jardvcode.bot.report.dto.report;

public final class ReportDTOMother {

    public static ReportDTO create() {
        return new ReportDTO(
                1L,
                HeaderDTOMother.create(),
                ResponseDTOMother.values()
        );
    }

}
