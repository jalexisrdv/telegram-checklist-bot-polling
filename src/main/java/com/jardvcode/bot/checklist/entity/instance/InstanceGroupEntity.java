package com.jardvcode.bot.checklist.entity.instance;

import com.jardvcode.bot.checklist.domain.ChecklistStatus;
import com.jardvcode.bot.checklist.entity.template.GroupEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "checklist_instance_groups")
public final class InstanceGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long instanceId;

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @Column(name = "option_number")
    private Long optionNumber;

    @Column
    private String status;

    public static InstanceGroupEntity withCompletedStatus(Long instanceId, Long groupId) {
        InstanceGroupEntity entity = new InstanceGroupEntity();
        entity.setInstanceId(instanceId);
        entity.setOptionNumber(groupId);
        entity.setStatus(ChecklistStatus.COMPLETADO.name());
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

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
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

}
