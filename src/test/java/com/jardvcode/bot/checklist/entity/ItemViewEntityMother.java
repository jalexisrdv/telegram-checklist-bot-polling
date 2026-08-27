package com.jardvcode.bot.checklist.entity;

import java.util.ArrayList;
import java.util.List;

public final class ItemViewEntityMother {

    public static ItemViewEntity motorItem() {
        SectionViewEntity section = createSection(3L, "Motor");
        return createItem(1L, section, "Revisión de fugas (aceite, agua, diésel)", 1);
    }

    public static List<ItemViewEntity> motorItems() {
        List<ItemViewEntity> items = new ArrayList<>();

        SectionViewEntity section = createSection(3L, "Motor");

        items.add(createItem(1L, section, "Revisión de fugas (aceite, agua, diésel)", 1));
        items.add(createItem(1L, section, "Reset Insite", 2));
        items.add(createItem(1L, section, "Cambio de filtros (diésel)", 3));
        items.add(createItem(1L, section, "Revisar tensión de bandas", 4));
        items.add(createItem(1L, section, "Nivel de refrigerante", 5));
        items.add(createItem(1L, section, "Cambiar filtro de aire según indicador de partículas", 6));

        return items;
    }

    public static List<ItemViewEntity> checklistItems() {
        List<ItemViewEntity> items = new ArrayList<>();

        SectionViewEntity systemDirection = createSection(1L, "Sistema de dirección");
        items.add(createItem(1L, systemDirection, "Revisar nivel de aceite", 1));

        SectionViewEntity frontSuspension = createSection(2L, "Suspensión delantera");
        items.add(createItem(2L, frontSuspension, "Revisar abrazaderas", 1));

        SectionViewEntity motor = createSection(3L, "Motor");
        items.add(createItem(3L, motor, "Revisión de fugas (aceite, agua, diésel)", 1));
        items.add(createItem(4L, motor, "Reset Insite", 2));
        items.add(createItem(5L, motor, "Cambio de filtros (diésel)", 3));
        items.add(createItem(6L, motor, "Revisar tensión de bandas", 4));
        items.add(createItem(7L, motor, "Nivel de refrigerante", 5));
        items.add(createItem(8L, motor, "Cambiar filtro de aire según indicador de partículas", 6));

        SectionViewEntity clutch = createSection(4L, "Embrague");
        items.add(createItem(9L, clutch, "Ajuste o calibrar", 1));
        items.add(createItem(10L, clutch, "Revisión líquido", 2));

        SectionViewEntity transmission = createSection(5L, "Transmisión");
        items.add(createItem(11L, transmission, "Revisar nivel de aceite", 1));
        items.add(createItem(12L, transmission, "Revisar fugas de aceite", 2));
        items.add(createItem(13L, transmission, "Revisar fugas de aire", 3));

        SectionViewEntity differentials = createSection(6L, "Diferenciales");
        items.add(createItem(14L, differentials, "Revisar nivel de aceite", 1));
        items.add(createItem(15L, differentials, "Revisar fugas de aceite", 2));

        SectionViewEntity fifthWheel = createSection(7L, "Quintarueda");
        items.add(createItem(16L, fifthWheel, "Limpieza general", 1));
        items.add(createItem(17L, fifthWheel, "Lubricación", 2));

        SectionViewEntity electricSystem = createSection(8L, "Sistema eléctrico/electrónico");
        items.add(createItem(18L, electricSystem, "Checar baterías y terminales", 1));
        items.add(createItem(19L, electricSystem, "Checar arneses y cables (sueltos)", 2));
        items.add(createItem(20L, electricSystem, "Revisar luces", 3));
        items.add(createItem(21L, electricSystem, "Rev. alarma de reversa", 4));
        items.add(createItem(22L, electricSystem, "Revisar códigos de falla de motor en tablero de instrumentos", 5));

        SectionViewEntity bearings = createSection(9L, "Sistema de rodamiento");
        items.add(createItem(23L, bearings, "Revisar presión (100 lbs)", 1));
        items.add(createItem(24L, bearings, "Revisar marcaje", 2));

        SectionViewEntity brakes = createSection(10L, "Sistema de frenos");
        items.add(createItem(25L, brakes, "Revisar fugas de aire", 1));
        items.add(createItem(26L, brakes, "Purgar tanques de aire", 2));
        items.add(createItem(27L, brakes, "Calibrar frenos", 3));

        SectionViewEntity safety = createSection(11L, "Equipo de seguridad");
        items.add(createItem(28L, safety, "Revisar cinturones de seguridad", 1));
        items.add(createItem(29L, safety, "Revisar porta extintor", 2));

        SectionViewEntity cleaning = createSection(12L, "Limpieza y lubricación");
        items.add(createItem(30L, cleaning, "Lavado de motor y carrocería", 1));
        items.add(createItem(31L, cleaning, "Engrasado general", 2));

        return items;
    }

    private static SectionViewEntity createSection(Long id, String name) {
        SectionViewEntity section = new SectionViewEntity();

        section.setId(id);
        section.setName(name);

        return section;
    }

    private static ItemViewEntity createItem(Long id, SectionViewEntity section, String label, Integer optionNumber) {
        ItemViewEntity item = new ItemViewEntity();

        item.setId(id);
        item.setSectionId(section.getSectionId());
        item.setLabel(label);
        item.setOptionNumber(optionNumber);

        return item;
    }

}
