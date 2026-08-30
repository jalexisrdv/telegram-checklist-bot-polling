package com.jardvcode.bot.checklist.entity;

import com.jardvcode.bot.checklist.domain.StatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "assignment_sections_view")
public final class SectionViewEntity {

    @Id
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "option_number")
    private Integer optionNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Set<ItemViewEntity> items = new HashSet<>();

    public int totalItems() {
        return items.size();
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(Integer optionNumber) {
        this.optionNumber = optionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ItemViewEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemViewEntity> items) {
        this.items = items;
    }

}
