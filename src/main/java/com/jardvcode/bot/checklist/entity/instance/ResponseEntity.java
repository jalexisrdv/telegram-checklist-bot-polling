package com.jardvcode.bot.checklist.entity.instance;

import com.jardvcode.bot.checklist.entity.template.ItemEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "checklist_responses")
public final class ResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long instanceId;

    @Column
    private Long groupId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(name = "option_number")
    private Long optionNumber;

    @Column
    private String status;

    @Column
    private String observation;

    public static ResponseEntity create(Long id, String status, String observation) {
        ResponseEntity entity = new ResponseEntity();

        entity.setId(id);
        entity.setStatus(status);
        entity.setObservation(observation);

        return entity;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public Long getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(Long optionNumber) {
        this.optionNumber = optionNumber;
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
