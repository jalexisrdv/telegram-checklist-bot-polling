package com.jardvcode.bot.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bot_session_data")
public final class BotSessionDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String state;

    @Column
    private String key;

    @Column
    private String value;

    public static BotSessionDataEntity create(Long userId, String state, String key, String value) {
        BotSessionDataEntity entity = new BotSessionDataEntity();

        entity.setUserId(userId);
        entity.setState(state);
        entity.setKey(key);
        entity.setValue(value);

        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
