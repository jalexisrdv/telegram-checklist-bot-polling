package com.jardvcode.bot.user.entity;

import java.time.LocalDateTime;

public final class UserLinkTokenEntityMother {

    public static UserLinkTokenEntity withUsedToken() {
        UserLinkTokenEntity entity = new UserLinkTokenEntity();
        entity.setToken("550e8400-e29b-41d4-a716-446655440000");
        entity.setExpiresAt(LocalDateTime.now().plusDays(1));
        entity.setUsed(true);
        return entity;
    }

    public static UserLinkTokenEntity withExpiredToken() {
        UserLinkTokenEntity entity = new UserLinkTokenEntity();
        entity.setToken("550e8400-e29b-41d4-a716-446655440000");
        entity.setExpiresAt(LocalDateTime.now().minusDays(2));
        return entity;
    }

    public static UserLinkTokenEntity withValidToken() {
        UserLinkTokenEntity entity = new UserLinkTokenEntity();
        entity.setUserId(1L);
        entity.setToken("550e8400-e29b-41d4-a716-446655440000");
        entity.setExpiresAt(LocalDateTime.now().plusDays(1));
        entity.setUsed(false);
        return entity;
    }

}