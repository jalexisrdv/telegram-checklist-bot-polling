package com.jardvcode.bot.checklist.entity.template;

import jakarta.persistence.*;

@Entity
@Table(name = "checklist_items")
public final class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long groupId;

    @Column
    private String description;

    public static ItemEntity create(Long id, Long groupId, String description) {
        ItemEntity entity = new ItemEntity();
        entity.setId(id);
        entity.setGroupId(groupId);
        entity.setDescription(description);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
