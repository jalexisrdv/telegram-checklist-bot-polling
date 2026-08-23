package com.jardvcode.bot.checklist.entity.instance;

import jakarta.persistence.*;

@Entity
@Table(name = "assignment_responses")
public final class ResponseEntity {

    @Id
    private Long id;

    @Column(name = "assignment_id")
    private Long instanceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String observation;

    public Integer optionNumber() {
        return item.getOptionNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}