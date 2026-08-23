package com.jardvcode.bot.user.repository;

import com.jardvcode.bot.user.entity.BotActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BotActivationTokenRepository extends JpaRepository<BotActivationTokenEntity, Long> {
    Optional<BotActivationTokenEntity> findByToken(String token);
}
