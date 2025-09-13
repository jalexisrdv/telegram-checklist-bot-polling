package com.jardvcode.bot.shared.domain.exception;

public final class UnexpectedException extends RuntimeException {

    public UnexpectedException() {
        super("Internal system error");
    }

}
