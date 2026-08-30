package com.jardvcode.bot.checklist.entity;

import com.jardvcode.bot.checklist.domain.StatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Immutable
@Table(name = "checklist_assignments_view")
public final class AssignmentViewEntity {

    @Id
    private Long id;

    @Column(name = "mechanic_user_id")
    private Long mechanicUserId;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "unit_number")
    private Integer unitNumber;

    @Column(name = "operator_full_name")
    private String operatorFullName;

    @Column(name = "mechanic_full_name")
    private String mechanicFullName;

    @Column(name = "mileage")
    private String mileage;

    @Column(name = "next_service")
    private String nextService;

    @Column(name = "time_in")
    private LocalTime timeIn;

    @Column(name = "time_out")
    private LocalTime timeOut;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "option_number")
    private Integer optionNumber;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Set<SectionViewEntity> sections = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMechanicUserId() {
        return mechanicUserId;
    }

    public void setMechanicUserId(Long mechanicUserId) {
        this.mechanicUserId = mechanicUserId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getOperatorFullName() {
        return operatorFullName;
    }

    public void setOperatorFullName(String operatorFullName) {
        this.operatorFullName = operatorFullName;
    }

    public String getMechanicFullName() {
        return mechanicFullName;
    }

    public void setMechanicFullName(String mechanicFullName) {
        this.mechanicFullName = mechanicFullName;
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

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Set<SectionViewEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionViewEntity> sections) {
        this.sections = sections;
    }

}
