package com.jardvcode.bot.checklist.entity.template;

import jakarta.persistence.*;

@Entity
@Table(name = "checklist_groups")
public final class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long templateId;

    @Column
    private String name;

    public static GroupEntity create(Long id, String name, Long templateId) {
        GroupEntity group = new GroupEntity();
        group.setId(id);
        group.setName(name);
        group.setTemplateId(templateId);
        return group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
