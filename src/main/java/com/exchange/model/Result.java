package com.exchange.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    ErrorCode error;
    T data;

    public Boolean isSuccess() {
        return error == null;
    }
}
