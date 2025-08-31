package com.jardvcode.bot.user.repository;

import com.jardvcode.bot.user.entity.UserLinkTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLinkTokenRepository extends JpaRepository<UserLinkTokenEntity, Long> {
    Optional<UserLinkTokenEntity> findByToken(String token);
}
