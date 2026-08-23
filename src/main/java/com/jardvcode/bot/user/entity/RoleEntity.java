package com.jardvcode.bot.user.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<PermissionEntity> permissions;

    public static RoleEntity create(Long id, String name) {
        RoleEntity entity = new RoleEntity();

        entity.setId(id);
        entity.setName(name);

        return entity;
    }

    public void update(String name) {
        this.name = name;
    }

    public void assignPermissions(List<Long> permissionIds) {
        List<PermissionEntity> permissions = permissionIds.stream()
                .map(PermissionEntity::withId)
                .toList();

        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

}
