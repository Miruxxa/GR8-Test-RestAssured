package org.GR8.api.utils.enums;

import lombok.Getter;

@Getter
public enum StatusCodes {

    OK(200),
    CREATED(201),
    DELETED(204),
    RESET_CONTENT(205),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    GONE(410),
    UNPROCESSABLE_ENTITY(422);

    private final int code;

    StatusCodes(final int code) {
        this.code = code;
    }
}
