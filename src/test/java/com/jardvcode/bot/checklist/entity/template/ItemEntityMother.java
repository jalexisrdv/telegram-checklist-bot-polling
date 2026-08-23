package com.jardvcode.bot.checklist.entity.template;

import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import com.jardvcode.bot.checklist.entity.instance.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public final class ItemEntityMother {

    public static ItemEntity motorItem() {
        InstanceGroupEntity section = createSection(3L, "MOTOR");
        return createItem(1L, section, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)", 1);
    }

    public static List<ItemEntity> motorItems() {
        List<ItemEntity> items = new ArrayList<>();

        InstanceGroupEntity section = createSection(3L, "MOTOR");

        items.add(createItem(1L, section, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)", 1));
        items.add(createItem(1L, section, "RESET INSITE", 2));
        items.add(createItem(1L, section, "CAMBIO DE FILTROS (DIESEL)", 3));
        items.add(createItem(1L, section, "REVISAR TENSION DE BANDAS", 4));
        items.add(createItem(1L, section, "NIVEL DE REFRIGERANTE", 5));
        items.add(createItem(1L, section, "CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS", 6));

        return items;
    }

    public static List<ItemEntity> checklistItems() {
        List<ItemEntity> items = new ArrayList<>();

        InstanceGroupEntity systemDirection = createSection(1L, "SISTEMA DE DIRECCION");
        items.add(createItem(1L, systemDirection, "REVISAR NIVEL DE ACEITE", 1));

        InstanceGroupEntity frontSuspension = createSection(2L, "SUSPENCION DELANTERA");
        items.add(createItem(2L, frontSuspension, "REVISAR ABRAZADERAS", 1));

        InstanceGroupEntity motor = createSection(3L, "MOTOR");
        items.add(createItem(3L, motor, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)", 1));
        items.add(createItem(4L, motor, "RESET INSITE", 2));
        items.add(createItem(5L, motor, "CAMBIO DE FILTROS (DIESEL)", 3));
        items.add(createItem(6L, motor, "REVISAR TENSION DE BANDAS", 4));
        items.add(createItem(7L, motor, "NIVEL DE REFRIGERANTE", 5));
        items.add(createItem(8L, motor, "CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS", 6));

        InstanceGroupEntity clutch = createSection(4L, "EMBRAGUE");
        items.add(createItem(9L, clutch, "AJUSTE O CALIBRAR", 1));
        items.add(createItem(10L, clutch, "REVISION LIQUIDO", 2));

        InstanceGroupEntity transmission = createSection(5L, "TRANSMISION");
        items.add(createItem(11L, transmission, "REVISAR NIVEL DE ACEITE", 1));
        items.add(createItem(12L, transmission, "REVISAR FUGAS DE ACEITE", 2));
        items.add(createItem(13L, transmission, "REVISAR FUGAS DE AIRE", 3));

        InstanceGroupEntity differentials = createSection(6L, "DIFERENCIALES");
        items.add(createItem(14L, differentials, "REVISAR NIVEL DE ACEITE", 1));
        items.add(createItem(15L, differentials, "REVISAR FUGAS DE ACEITE", 2));

        InstanceGroupEntity fifthWheel = createSection(7L, "QUINTARUEDA");
        items.add(createItem(16L, fifthWheel, "LIMPIEZA GENERAL", 1));
        items.add(createItem(17L, fifthWheel, "LUBRICACION", 2));

        InstanceGroupEntity electricSystem = createSection(8L, "SISTEMA ELECTRICO/ELECTRONICO");
        items.add(createItem(18L, electricSystem, "CHECAR BATERIAS Y TERMINALES", 1));
        items.add(createItem(19L, electricSystem, "CHECAR ARNESES Y CABLES (SUELTOS)", 2));
        items.add(createItem(20L, electricSystem, "REVISAR LUCES", 3));
        items.add(createItem(21L, electricSystem, "REV ALARMA DE REVERSA", 4));
        items.add(createItem(22L, electricSystem, "REVISAR CODIGOS DE FALLA DE MOTOR EN TABLERO DE INSTRUMENTOS", 5));

        InstanceGroupEntity bearings = createSection(9L, "SISTEMA DE RODAMIENTO");
        items.add(createItem(23L, bearings, "REVISAR PRESION (100LBS)", 1));
        items.add(createItem(24L, bearings, "REVISAR MARCAJE", 2));

        InstanceGroupEntity brakes = createSection(10L, "SISTEMA DE FRENOS");
        items.add(createItem(25L, brakes, "REVISAR FUGAS DE AIRE", 1));
        items.add(createItem(26L, brakes, "PURGAR TANQUES DE AIRE", 2));
        items.add(createItem(27L, brakes, "CALIBRAR FRENOS", 3));

        InstanceGroupEntity safety = createSection(11L, "EQUIPO DE SEGURIDAD");
        items.add(createItem(28L, safety, "REVISAR CINTURONES DE SEGURIDAD", 1));
        items.add(createItem(29L, safety, "REVISAR PORTA EXTINTOR", 2));

        InstanceGroupEntity cleaning = createSection(12L, "LIMPIEZA Y LUBRICACION");
        items.add(createItem(30L, cleaning, "LAVADO DE MOTOR Y CARROCERIA", 1));
        items.add(createItem(31L, cleaning, "ENGRASADO GENERAL", 2));

        return items;
    }

    private static InstanceGroupEntity createSection(Long id, String name) {
        InstanceGroupEntity section = new InstanceGroupEntity();

        section.setId(id);
        section.setName(name);

        return section;
    }

    private static ItemEntity createItem(Long id, InstanceGroupEntity section, String label, Integer optionNumber) {
        ItemEntity item = new ItemEntity();

        item.setId(id);
        item.setSectionId(section.getSectionId());
        item.setLabel(label);
        item.setOptionNumber(optionNumber);

        return item;
    }

}
