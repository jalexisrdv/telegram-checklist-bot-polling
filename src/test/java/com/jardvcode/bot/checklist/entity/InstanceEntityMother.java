package com.jardvcode.bot.checklist.entity;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.entity.template.ChecklistTemplateEntity;

import java.time.LocalDate;
import java.util.ArrayList;

public final class InstanceEntityMother {

    public static ArrayList<InstanceEntity> values() {
        ArrayList<InstanceEntity> instances = new ArrayList<>();

        instances.add(withPendingStatus());
        instances.add(withCompletedStatus());

        return instances;
    }

    public static InstanceEntity withPendingStatus() {
        InstanceEntity instance = create();
        instance.setStatus("PENDIENTE");
        return instance;
    }

    public static InstanceEntity withCompletedStatus() {
        InstanceEntity instance = create();
        instance.setStatus("COMPLETADO");
        return instance;
    }

    public static InstanceEntity create() {
        ChecklistTemplateEntity template = new ChecklistTemplateEntity();
        template.setId(1L);
        template.setName("Formato para servicios A y C (BASICO)");

        InstanceEntity instance = new InstanceEntity();
        instance.setId(1L);
        instance.setTemplate(template);
        instance.setUserId(1L);
        instance.setOperatorName("PEDRO OCELOT");
        instance.setMileage("1299961");
        instance.setNextService("1,300,000 BASICO");
        instance.setOptionNumber(1L);
        instance.setDate(LocalDate.now());
        instance.setStatus("PENDIENTE");

        return instance;
    }

}
