package com.jardvcode.bot.checklist.entity.instance;

import com.jardvcode.bot.checklist.domain.ChecklistStatus;
import com.jardvcode.bot.checklist.entity.template.ChecklistTemplateEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "checklist_instances")
public final class InstanceEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "template_id")
    private ChecklistTemplateEntity template;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "option_number")
    private Long optionNumber;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "mileage")
    private String mileage;

    @Column(name = "next_service")
    private String nextService;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private String status;

    public static InstanceEntity withCompletedStatus(Long instanceId) {
        InstanceEntity entity = new InstanceEntity();
        entity.setId(instanceId);
        entity.setStatus(ChecklistStatus.COMPLETADO.name());
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChecklistTemplateEntity getTemplate() {
        return template;
    }

    public void setTemplate(ChecklistTemplateEntity template) {
        this.template = template;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(Long optionNumber) {
        this.optionNumber = optionNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getNextService() {
        return nextService;
    }

    public void setNextService(String nextService) {
        this.nextService = nextService;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
