package com.jardvcode.bot.checklist.dto;

public record ChecklistDTO(Long instanceId, Long templateId, String name, String date, String operatorName, String mileage, String nextService) {
}
