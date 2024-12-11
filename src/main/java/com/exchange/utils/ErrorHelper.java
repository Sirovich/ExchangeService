package com.exchange.utils;

import com.exchange.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class ErrorHelper {
    public static HttpStatus processError(ErrorCode error) {
        return switch (error) {
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;

            case WRONG_CREDENTIALS,
                 TRANSACTION_NOT_FOUND,
                 USER_NOT_FOUND -> HttpStatus.NOT_FOUND;

            case EMAIL_ALREADY_EXISTS -> HttpStatus.CONFLICT;

            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
