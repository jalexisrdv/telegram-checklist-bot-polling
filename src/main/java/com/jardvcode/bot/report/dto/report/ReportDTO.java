package com.jardvcode.bot.report.dto.report;

import java.util.List;

public record ReportDTO(Long instanceId, HeaderDTO header, List<ResponseDTO> responses) {

}
