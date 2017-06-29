package com.sample.galleryapp.common.exceptions;

public interface ErrorBundle {
    String getErrorMessage();

    Exception getCauseException();
}
