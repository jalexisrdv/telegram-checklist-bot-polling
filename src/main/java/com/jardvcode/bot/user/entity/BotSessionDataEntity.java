package com.jardvcode.bot.user.entity;

import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.shared.domain.state.StateUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "bot_session_data")
public final class BotSessionDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String platformUserId;

    @Column
    private String state;

    @Column
    private String key;

    @Column
    private String value;

    public static BotSessionDataEntity create(String platformUserId, Class<? extends State> state, Enum<?> key, String value) {
        BotSessionDataEntity entity = new BotSessionDataEntity();

        entity.setPlatformUserId(platformUserId);
        entity.setState(StateUtil.uniqueName(state));
        entity.setKey(key.name());
        entity.setValue(value);

        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(String platformUserId) {
        this.platformUserId = platformUserId;
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
