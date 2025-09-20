package com.jardvcode.bot.report.dto.report;

import java.util.List;

public record ReportDTO(Long checklistId, HeaderDTO header, List<ResponseDTO> responses) {

}
