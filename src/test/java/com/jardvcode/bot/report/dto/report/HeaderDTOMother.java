package com.jardvcode.bot.report.dto.report;

import java.util.HashMap;
import java.util.Map;

public final class HeaderDTOMother {

    public static HeaderDTO create() {
        return new HeaderDTO(
                "273",
                "Formato para servicios A y C (BASICO)",
                "PEDRO OCELOT",
                "CATARINO",
                "1299961",
                "1,300,000 BASICO",
                "09:00:00",
                "",
                "10/09/2025"
        );
    }

    public static Map<String, String> createHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("unitNumber", "273");
        headerMap.put("templateName", "Formato para servicios A y C (BASICO)");
        headerMap.put("operatorName", "PEDRO OCELOT");
        headerMap.put("mechanic", "CATARINO");
        headerMap.put("mileage", "1299961");
        headerMap.put("nextService", "1,300,000 BASICO");
        headerMap.put("timeIn", "09:00:00");
        headerMap.put("timeOut", "");
        headerMap.put("date", "10/09/2025");
        return headerMap;
    }

}
