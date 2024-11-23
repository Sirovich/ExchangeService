package com.exchange.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    long id;
    String email;
    String password;
    UserType type;
}
