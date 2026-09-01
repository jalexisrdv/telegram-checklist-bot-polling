package com.jardvcode.bot.checklist.entity;

import com.jardvcode.bot.checklist.domain.StatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "assignment_responses")
public final class ResponseEntity {

    @Id
    private Long id;

    @Column(name = "assignment_id")
    private Long assignmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private ItemViewEntity item;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    public void update(String status, String comment) {
        this.status = status.toUpperCase();
        this.comment = comment;
    }

    public StatusEnum status() {
        if(status == null) {
            return StatusEnum.PENDING;
        }

        return StatusEnum.COMPLETED;
    }

    public String value() {
        if(status == null) {
            return "";
        }

        return status + " " + comment;
    }

    public Integer optionNumber() {
        return item.getOptionNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public ItemViewEntity getItem() {
        return item;
    }

    public void setItem(ItemViewEntity item) {
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}