package com.exchange.model.dto;

import com.exchange.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDto {
    long id;
    String email;
    String password;
    UserType type;
}
