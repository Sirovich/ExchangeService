package com.exchange.repository.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
public class BaseEntity {
    Date createdAt;
    Date updatedAt;

    BaseEntity(Date createdAt, Date updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
