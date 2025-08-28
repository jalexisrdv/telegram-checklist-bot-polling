package com.jardvcode.bot.shared.domain.repository;

import com.jardvcode.bot.shared.domain.entity.UserBotStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBotStateRepository extends JpaRepository<UserBotStateEntity, Long> {

    Optional<UserBotStateEntity> findByPlatformUserId(String userId);

}
