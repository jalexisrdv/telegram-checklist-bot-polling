package com.jardvcode.bot.checklist.entity.template;

import com.jardvcode.bot.checklist.entity.instance.ItemEntity;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;

import java.util.ArrayList;
import java.util.List;

public final class ItemEntityMother {

    public static ItemEntity motorItem() {
        InstanceGroupEntity group = createGroup(3L, "MOTOR");
        return createItem(1L, group, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)", 1);
    }

    public static List<ItemEntity> motorItems() {
        List<ItemEntity> items = new ArrayList<>();

        InstanceGroupEntity group = createGroup(3L, "MOTOR");

        items.add(createItem(1L, group, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)", 1));
        items.add(createItem(1L, group, "RESET INSITE", 2));
        items.add(createItem(1L, group, "CAMBIO DE FILTROS (DIESEL)", 3));
        items.add(createItem(1L, group, "REVISAR TENSION DE BANDAS", 4));
        items.add(createItem(1L, group, "NIVEL DE REFRIGERANTE", 5));
        items.add(createItem(1L, group, "CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS", 6));

        return items;
    }

    public static List<ItemEntity> checklistItems() {
        List<ItemEntity> items = new ArrayList<>();

        InstanceGroupEntity systemDirection = createGroup(1L, "SISTEMA DE DIRECCION");
        items.add(createItem(1L, systemDirection, "REVISAR NIVEL DE ACEITE", 1));

        InstanceGroupEntity frontSuspension = createGroup(2L, "SUSPENCION DELANTERA");
        items.add(createItem(2L, frontSuspension, "REVISAR ABRAZADERAS", 1));

        InstanceGroupEntity motor = createGroup(3L, "MOTOR");
        items.add(createItem(3L, motor, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)", 1));
        items.add(createItem(4L, motor, "RESET INSITE", 2));
        items.add(createItem(5L, motor, "CAMBIO DE FILTROS (DIESEL)", 3));
        items.add(createItem(6L, motor, "REVISAR TENSION DE BANDAS", 4));
        items.add(createItem(7L, motor, "NIVEL DE REFRIGERANTE", 5));
        items.add(createItem(8L, motor, "CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS", 6));

        InstanceGroupEntity clutch = createGroup(4L, "EMBRAGUE");
        items.add(createItem(9L, clutch, "AJUSTE O CALIBRAR", 1));
        items.add(createItem(10L, clutch, "REVISION LIQUIDO", 2));

        InstanceGroupEntity transmission = createGroup(5L, "TRANSMISION");
        items.add(createItem(11L, transmission, "REVISAR NIVEL DE ACEITE", 1));
        items.add(createItem(12L, transmission, "REVISAR FUGAS DE ACEITE", 2));
        items.add(createItem(13L, transmission, "REVISAR FUGAS DE AIRE", 3));

        InstanceGroupEntity differentials = createGroup(6L, "DIFERENCIALES");
        items.add(createItem(14L, differentials, "REVISAR NIVEL DE ACEITE", 1));
        items.add(createItem(15L, differentials, "REVISAR FUGAS DE ACEITE", 2));

        InstanceGroupEntity fifthWheel = createGroup(7L, "QUINTARUEDA");
        items.add(createItem(16L, fifthWheel, "LIMPIEZA GENERAL", 1));
        items.add(createItem(17L, fifthWheel, "LUBRICACION", 2));

        InstanceGroupEntity electricSystem = createGroup(8L, "SISTEMA ELECTRICO/ELECTRONICO");
        items.add(createItem(18L, electricSystem, "CHECAR BATERIAS Y TERMINALES", 1));
        items.add(createItem(19L, electricSystem, "CHECAR ARNESES Y CABLES (SUELTOS)", 2));
        items.add(createItem(20L, electricSystem, "REVISAR LUCES", 3));
        items.add(createItem(21L, electricSystem, "REV ALARMA DE REVERSA", 4));
        items.add(createItem(22L, electricSystem, "REVISAR CODIGOS DE FALLA DE MOTOR EN TABLERO DE INSTRUMENTOS", 5));

        InstanceGroupEntity bearings = createGroup(9L, "SISTEMA DE RODAMIENTO");
        items.add(createItem(23L, bearings, "REVISAR PRESION (100LBS)", 1));
        items.add(createItem(24L, bearings, "REVISAR MARCAJE", 2));

        InstanceGroupEntity brakes = createGroup(10L, "SISTEMA DE FRENOS");
        items.add(createItem(25L, brakes, "REVISAR FUGAS DE AIRE", 1));
        items.add(createItem(26L, brakes, "PURGAR TANQUES DE AIRE", 2));
        items.add(createItem(27L, brakes, "CALIBRAR FRENOS", 3));

        InstanceGroupEntity safety = createGroup(11L, "EQUIPO DE SEGURIDAD");
        items.add(createItem(28L, safety, "REVISAR CINTURONES DE SEGURIDAD", 1));
        items.add(createItem(29L, safety, "REVISAR PORTA EXTINTOR", 2));

        InstanceGroupEntity cleaning = createGroup(12L, "LIMPIEZA Y LUBRICACION");
        items.add(createItem(30L, cleaning, "LAVADO DE MOTOR Y CARROCERIA", 1));
        items.add(createItem(31L, cleaning, "ENGRASADO GENERAL", 2));

        return items;
    }

    private static InstanceGroupEntity createGroup(Long id, String name) {
        InstanceGroupEntity group = new InstanceGroupEntity();

        group.setId(id);
        group.setName(name);

        return group;
    }

    private static ItemEntity createItem(Long id, InstanceGroupEntity section, String label, Integer optionNumber) {
        ItemEntity entity = new ItemEntity();

        entity.setId(id);
        entity.setGroupId(section.getGroupId());
        entity.setLabel(label);
        entity.setOptionNumber(optionNumber);

        return entity;
    }

}
