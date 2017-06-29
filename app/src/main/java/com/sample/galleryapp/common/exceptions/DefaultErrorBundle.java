package com.sample.galleryapp.common.exceptions;

public class DefaultErrorBundle implements ErrorBundle {
    private final Exception cause;

    public DefaultErrorBundle(final Exception cause) {
        this.cause = cause;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public Exception getCauseException() {
        return cause;
    }
}
