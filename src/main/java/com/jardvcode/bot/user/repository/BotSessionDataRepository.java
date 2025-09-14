package com.jardvcode.bot.user.repository;

import com.jardvcode.bot.user.entity.BotSessionDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BotSessionDataRepository extends JpaRepository<BotSessionDataEntity, Long> {
    Optional<BotSessionDataEntity> findByUserIdAndKey(Long userId, String key);
    void deleteByUserId(Long userId);
}
