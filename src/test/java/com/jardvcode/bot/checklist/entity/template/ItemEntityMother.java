package com.jardvcode.bot.checklist.entity.template;

import java.util.ArrayList;
import java.util.List;

public final class ItemEntityMother {

    public static ItemEntity motorItem() {
        GroupEntity group = createGroup(3L, "MOTOR");
        return createItem(1L, group, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)");
    }

    public static List<ItemEntity> motorItems() {
        List<ItemEntity> items = new ArrayList<>();

        GroupEntity group = createGroup(3L, "MOTOR");

        items.add(createItem(1L, group, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)"));
        items.add(createItem(1L, group, "RESET INSITE"));
        items.add(createItem(1L, group, "CAMBIO DE FILTROS (DIESEL)"));
        items.add(createItem(1L, group, "REVISAR TENSION DE BANDAS"));
        items.add(createItem(1L, group, "NIVEL DE REFRIGERANTE"));
        items.add(createItem(1L, group, "CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS"));

        return items;
    }

    public static List<ItemEntity> checklistItems() {
        List<ItemEntity> items = new ArrayList<>();

        GroupEntity systemDirection = createGroup(1L, "SISTEMA DE DIRECCION");
        items.add(createItem(1L, systemDirection, "REVISAR NIVEL DE ACEITE"));

        GroupEntity frontSuspension = createGroup(2L, "SUSPENCION DELANTERA");
        items.add(createItem(2L, frontSuspension, "REVISAR ABRAZADERAS"));

        GroupEntity motor = createGroup(3L, "MOTOR");
        items.add(createItem(3L, motor, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)"));
        items.add(createItem(4L, motor, "RESET INSITE"));
        items.add(createItem(5L, motor, "CAMBIO DE FILTROS (DIESEL)"));
        items.add(createItem(6L, motor, "REVISAR TENSION DE BANDAS"));
        items.add(createItem(7L, motor, "NIVEL DE REFRIGERANTE"));
        items.add(createItem(8L, motor, "CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS"));

        GroupEntity clutch = createGroup(4L, "EMBRAGUE");
        items.add(createItem(9L, clutch, "AJUSTE O CALIBRAR"));
        items.add(createItem(10L, clutch, "REVISION LIQUIDO"));

        GroupEntity transmission = createGroup(5L, "TRANSMISION");
        items.add(createItem(11L, transmission, "REVISAR NIVEL DE ACEITE"));
        items.add(createItem(12L, transmission, "REVISAR FUGAS DE ACEITE"));
        items.add(createItem(13L, transmission, "REVISAR FUGAS DE AIRE"));

        GroupEntity differentials = createGroup(6L, "DIFERENCIALES");
        items.add(createItem(14L, differentials, "REVISAR NIVEL DE ACEITE"));
        items.add(createItem(15L, differentials, "REVISAR FUGAS DE ACEITE"));

        GroupEntity fifthWheel = createGroup(7L, "QUINTARUEDA");
        items.add(createItem(16L, fifthWheel, "LIMPIEZA GENERAL"));
        items.add(createItem(17L, fifthWheel, "LUBRICACION"));

        GroupEntity electricSystem = createGroup(8L, "SISTEMA ELECTRICO/ELECTRONICO");
        items.add(createItem(18L, electricSystem, "CHECAR BATERIAS Y TERMINALES"));
        items.add(createItem(19L, electricSystem, "CHECAR ARNESES Y CABLES (SUELTOS)"));
        items.add(createItem(20L, electricSystem, "REVISAR LUCES"));
        items.add(createItem(21L, electricSystem, "REV ALARMA DE REVERSA"));
        items.add(createItem(22L, electricSystem, "REVISAR CODIGOS DE FALLA DE MOTOR EN TABLERO DE INSTRUMENTOS"));

        GroupEntity bearings = createGroup(9L, "SISTEMA DE RODAMIENTO");
        items.add(createItem(23L, bearings, "REVISAR PRESION (100LBS)"));
        items.add(createItem(24L, bearings, "REVISAR MARCAJE"));

        GroupEntity brakes = createGroup(10L, "SISTEMA DE FRENOS");
        items.add(createItem(25L, brakes, "REVISAR FUGAS DE AIRE"));
        items.add(createItem(26L, brakes, "PURGAR TANQUES DE AIRE"));
        items.add(createItem(27L, brakes, "CALIBRAR FRENOS"));

        GroupEntity safety = createGroup(11L, "EQUIPO DE SEGURIDAD");
        items.add(createItem(28L, safety, "REVISAR CINTURONES DE SEGURIDAD"));
        items.add(createItem(29L, safety, "REVISAR PORTA EXTINTOR"));

        GroupEntity cleaning = createGroup(12L, "LIMPIEZA Y LUBRICACION");
        items.add(createItem(30L, cleaning, "LAVADO DE MOTOR Y CARROCERIA"));
        items.add(createItem(31L, cleaning, "ENGRASADO GENERAL"));

        return items;
    }

    private static GroupEntity createGroup(Long id, String name) {
        GroupEntity group = new GroupEntity();
        group.setId(id);
        group.setName(name);
        return group;
    }

    private static ItemEntity createItem(Long id, GroupEntity group, String description) {
        ItemEntity entity = new ItemEntity();
        entity.setId(id);
        entity.setGroup(group);
        entity.setDescription(description);

        return entity;
    }

}
