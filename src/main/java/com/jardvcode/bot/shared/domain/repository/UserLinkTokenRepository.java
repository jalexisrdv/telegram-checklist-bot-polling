package com.jardvcode.bot.shared.domain.repository;

import com.jardvcode.bot.shared.domain.entity.UserLinkTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLinkTokenRepository extends JpaRepository<UserLinkTokenEntity, Long> {
    Optional<UserLinkTokenEntity> findByToken(String token);
}
