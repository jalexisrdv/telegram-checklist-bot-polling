package com.jardvcode.bot.user.repository;

import com.jardvcode.bot.user.entity.BotSessionDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface BotSessionDataRepository extends JpaRepository<BotSessionDataEntity, Long> {
    Optional<BotSessionDataEntity> findByPlatformUserIdAndKey(String platformUserId, String key);
    void deleteByPlatformUserId(String platformUserId);
}
