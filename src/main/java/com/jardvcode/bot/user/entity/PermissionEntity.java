package com.jardvcode.bot.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public static PermissionEntity create(Long id, Long moduleId, String code, String name, String description) {
        PermissionEntity entity = new PermissionEntity();

        entity.id = id;
        entity.moduleId = moduleId;
        entity.code = code;
        entity.name = name;
        entity.description = description;

        return entity;
    }

    public void update(Long moduleId, String code, String name, String description) {
        this.moduleId = moduleId;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static PermissionEntity withId(Long id) {
        PermissionEntity entity = new PermissionEntity();

        entity.id = id;

        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}