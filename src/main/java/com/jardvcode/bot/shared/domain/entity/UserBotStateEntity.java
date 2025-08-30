package com.jardvcode.bot.shared.domain.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_bot_states")
public class UserBotStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // corresponde a SERIAL
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "platform_user_id")
    private String platformUserId;

    @Column(name = "current_state")
    private String currentState;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permissions", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<PermissionEntity> permissions = new HashSet<>();

    public static UserBotStateEntity create(String platformUserId, String currentState) {
        UserBotStateEntity entity = new UserBotStateEntity();
        entity.setPlatformUserId(platformUserId);
        entity.setCurrentState(currentState);
        return entity;
    }

    public Set<String> permissions() {
        return permissions.stream()
                .map(PermissionEntity::getCode)
                .collect(Collectors.toSet());
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
