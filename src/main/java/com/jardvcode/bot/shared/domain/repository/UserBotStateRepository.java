package com.jardvcode.bot.shared.domain.repository;

import com.jardvcode.bot.shared.domain.entity.UserBotStateEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserBotStateRepository extends JpaRepository<UserBotStateEntity, Long> {

    Optional<UserBotStateEntity> findByPlatformUserId(String userId);

    @Modifying
    @Transactional
    @Query("UPDATE UserBotStateEntity u SET u.currentState = :currentState WHERE u.platformUserId = :platformUserId")
    void updateCurrentStateByPlatformUserId(String platformUserId, String currentState);

}
