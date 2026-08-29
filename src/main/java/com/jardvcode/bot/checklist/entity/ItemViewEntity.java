package com.jardvcode.bot.checklist.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Immutable
@Table(name = "assignment_items_view")
public final class ItemViewEntity {

    @Id
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "label")
    private String label;

    @Column(name = "option_number")
    private Integer optionNumber;

    @OneToOne(mappedBy = "item", fetch = FetchType.LAZY)
    private ResponseEntity response;

    public boolean hasResponse() {
        return response.getStatus() != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(Integer optionNumber) {
        this.optionNumber = optionNumber;
    }

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

}
