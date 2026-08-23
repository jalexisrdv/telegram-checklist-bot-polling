package com.jardvcode.bot.checklist.domain;

public enum BotCommand {

    ASSIGNMENTS("/listas"),
    SECTIONS("/grupos");

    private String value;

    private BotCommand(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
