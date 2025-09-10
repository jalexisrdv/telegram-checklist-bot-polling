package com.jardvcode.bot.checklist.domain;

public enum ChecklistStatus {
    PENDIENTE("\u23F3"),
    TRABAJANDO("\u1F527"),
    COMPLETADO("\u2705"),
    CONFIRMADO("\u2705");

    private final String emoji;

    ChecklistStatus(String emoji) {
        this.emoji = emoji;
    }

    public String emoji() {
        return emoji;
    }

    public static String fromStatus(String status) {
        if (status == null) return "";
        try {
            return ChecklistStatus.valueOf(status.toUpperCase()).emoji();
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}
