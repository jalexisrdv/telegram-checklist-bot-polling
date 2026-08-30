package com.jardvcode.bot.user.repository;

import com.jardvcode.bot.user.entity.BotUserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BotUserStateRepository extends JpaRepository<BotUserEntity, Long> {

    Optional<BotUserEntity> findByProviderUserId(String providerUserId);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<BotUserEntity> findWithRolesAndPermissionsByProviderUserId(String providerUserId);

}
