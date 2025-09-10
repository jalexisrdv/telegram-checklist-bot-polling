package com.jardvcode.bot.user.repository;

import com.jardvcode.bot.user.entity.BotUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserBotStateRepository extends JpaRepository<BotUserEntity, Long> {

    Optional<BotUserEntity> findByPlatformUserId(String userId);

    @Modifying
    @Transactional
    @Query("UPDATE BotUserEntity u SET u.currentState = :currentState WHERE u.platformUserId = :platformUserId")
    void updateCurrentStateByPlatformUserId(String platformUserId, String currentState);

}
