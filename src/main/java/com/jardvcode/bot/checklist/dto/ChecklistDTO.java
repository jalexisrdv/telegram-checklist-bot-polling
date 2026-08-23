package com.jardvcode.bot.checklist.dto;

public record ChecklistDTO(Long assignmentId, Long templateId, String name, String date, String operatorName, String mileage, String nextService) {
}
