package com.exchange.repository.entity;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@RequiredArgsConstructor
public class BaseEntity {
    Instant createdAt;
    Instant updatedAt;

    BaseEntity(Instant createdAt, Instant updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
