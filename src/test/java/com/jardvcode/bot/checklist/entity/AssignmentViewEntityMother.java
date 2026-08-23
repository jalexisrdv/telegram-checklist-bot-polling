package com.jardvcode.bot.checklist.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public final class AssignmentViewEntityMother {

    public static ArrayList<AssignmentViewEntity> values() {
        ArrayList<AssignmentViewEntity> assignments = new ArrayList<>();

        assignments.add(withPendingStatus());
        assignments.add(withCompletedStatus());

        return assignments;
    }

    public static AssignmentViewEntity withPendingStatus() {
        AssignmentViewEntity assignment = create();
        assignment.setStatus("PENDIENTE");
        return assignment;
    }

    public static AssignmentViewEntity withCompletedStatus() {
        AssignmentViewEntity assignment = create();
        assignment.setStatus("COMPLETADO");
        return assignment;
    }

    public static AssignmentViewEntity create() {
        AssignmentViewEntity assignment = new AssignmentViewEntity();

        assignment.setId(1L);
        assignment.setMechanicUserId(1L);
        assignment.setTemplateId(1L);
        assignment.setTemplateName("Formato para servicios A y C (BASICO)");
        assignment.setUnitNumber(243);
        assignment.setOperatorFullName("PEDRO OCELOT");
        assignment.setMechanicFullName("CATARINO");
        assignment.setMileage("1299961");
        assignment.setNextService("1,300,000 BASICO");
        assignment.setOptionNumber(1);
        assignment.setTimeIn(LocalTime.parse("09:00:00"));
        assignment.setTimeOut(LocalTime.parse("18:00:00"));
        assignment.setDate(LocalDate.now());
        assignment.setStatus("PENDIENTE");

        return assignment;
    }

}
