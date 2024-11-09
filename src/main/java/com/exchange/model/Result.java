package com.exchange.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    ErrorCode error;

    public Boolean isSuccess() {
        return error == null;
    }
}
