package com.jardvcode.bot.checklist.entity.instance;

import java.time.LocalDate;
import java.time.LocalTime;
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
        InstanceEntity instance = new InstanceEntity();

        instance.setId(1L);
        instance.setUserId(1L);
        instance.setTemplateId(1L);
        instance.setTemplateName("Formato para servicios A y C (BASICO)");
        instance.setUnitNumber(243);
        instance.setOperatorFullName("PEDRO OCELOT");
        instance.setMechanicFullName("CATARINO");
        instance.setMileage("1299961");
        instance.setNextService("1,300,000 BASICO");
        instance.setOptionNumber(1);
        instance.setTimeIn(LocalTime.parse("09:00:00"));
        instance.setTimeOut(LocalTime.parse("18:00:00"));
        instance.setDate(LocalDate.now());
        instance.setStatus("PENDIENTE");

        return instance;
    }

}
