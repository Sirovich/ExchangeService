package com.exchange.infrastructure.repositories;

import com.exchange.infrastructure.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryInterface extends JpaRepository<UserEntity, Long> {
}
