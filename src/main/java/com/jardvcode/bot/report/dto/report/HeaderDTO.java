package com.jardvcode.bot.report.dto.report;

import java.util.HashMap;
import java.util.Map;

public record HeaderDTO(
        String unitNumber,
        String templateName,
        String operatorName,
        String mechanic,
        String mileage,
        String nextService,
        String timeIn,
        String timeOut,
        String date
) {
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("unitNumber", unitNumber);
        map.put("templateName", templateName);
        map.put("operatorName", operatorName);
        map.put("mechanic", mechanic);
        map.put("mileage", mileage);
        map.put("nextService", nextService);
        map.put("timeIn", timeIn);
        map.put("timeOut", timeOut);
        map.put("date", date);
        return map;
    }
}
