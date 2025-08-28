package com.jardvcode.bot.shared.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_bot_state")
public class UserBotStateEntity {

    @Id
    @Column
    private String platformUserId;

    @Column
    private String currentState;

    public static UserBotStateEntity create(String platformUserId, String currentState) {
        UserBotStateEntity entity = new UserBotStateEntity();

        entity.setPlatformUserId(platformUserId);
        entity.setCurrentState(currentState);

        return entity;
    }

    public String getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(String platformUserId) {
        this.platformUserId = platformUserId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
