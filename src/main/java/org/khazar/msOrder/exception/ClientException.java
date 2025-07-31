package org.khazar.msOrder.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private final String errorCode;
    private final Integer statusCode;

    public ClientException(String code, String message, Integer statusCode) {
        super(message);
        this.errorCode = code;
        this.statusCode = statusCode;
    }
}