package com.jardvcode.bot.checklist.domain;

public enum BotCommand {

    CHECKLISTS("/listas"),
    GROUPS("/grupos");

    private String value;

    private BotCommand(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
