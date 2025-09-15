package com.jardvcode.bot.checklist.entity.template;

import java.util.ArrayList;
import java.util.List;

public final class GroupEntityMother {

    public static List<GroupEntity> values() {
        List<GroupEntity> groups = new ArrayList<>();

        groups.add(GroupEntity.create(1L, "SISTEMA DE DIRECCION", 1L));
        groups.add(GroupEntity.create(2L, "SUSPENCION DELANTERA", 2L));
        groups.add(GroupEntity.create(3L, "MOTOR", 3L));
        groups.add(GroupEntity.create(4L, "EMBRAGUE", 4L));
        groups.add(GroupEntity.create(5L, "TRANSMISION", 5L));
        groups.add(GroupEntity.create(6L, "DIFERENCIALES", 6L));
        groups.add(GroupEntity.create(7L, "QUINTARUEDA", 7L));
        groups.add(GroupEntity.create(8L, "SISTEMA ELECTRICO/ELECTRONICO", 8L));
        groups.add(GroupEntity.create(9L, "SISTEMA DE RODAMIENTO", 9L));
        groups.add(GroupEntity.create(10L, "SISTEMA DE FRENOS", 10L));
        groups.add(GroupEntity.create(11L, "EQUIPO DE SEGURIDAD", 11L));
        groups.add(GroupEntity.create(12L, "LIMPIEZA Y LUBRICACION", 12L));

        return groups;
    }

    public static GroupEntity create() {
        return GroupEntity.create(1L, "SISTEMA DE DIRECCION", 1L);
    }

}
