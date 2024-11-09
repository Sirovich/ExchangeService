package com.exchange.utils;

import com.exchange.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class ErrorHelper {
    public static HttpStatus processError(ErrorCode error) {
        switch (error){
            case ErrorCode.BAD_REQUEST -> {
                return HttpStatus.BAD_REQUEST;
            }
            case ErrorCode.USER_NOT_FOUND -> {
                return HttpStatus.NOT_FOUND;
            }
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
