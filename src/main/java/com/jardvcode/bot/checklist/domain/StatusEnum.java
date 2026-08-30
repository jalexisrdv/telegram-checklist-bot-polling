package com.jardvcode.bot.checklist.domain;


public enum StatusEnum {
    PENDING("\u23F3", "Pendiente"),
    IN_PROGRESS("\u1F527", "Trabajando"),
    COMPLETED("\u2705", "Completado"),
    CONFIRMED("\u2705", "Confirmado");

    private final String emoji;
    private final String label;

    StatusEnum(String emoji, String label) {
        this.emoji = emoji;
        this.label = label;
    }

    public String emoji() {
        return emoji;
    }

    public String label() {
        return label;
    }

}