package com.jardvcode.bot.checklist.entity.template;

import java.util.ArrayList;
import java.util.List;

public final class ItemEntityMother {

    public static List<ItemEntity> values() {
        List<ItemEntity> items = new ArrayList<>();

        items.add(ItemEntity.create(1L, 3L, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)"));
        items.add(ItemEntity.create(1L, 3L, "RESET INSITE"));
        items.add(ItemEntity.create(1L, 3L, "CAMBIO DE FILTROS (DIESEL)"));
        items.add(ItemEntity.create(1L, 3L, "REVISAR TENSION DE BANDAS"));
        items.add(ItemEntity.create(1L, 3L, "NIVEL DE REFRIGERANTE"));
        items.add(ItemEntity.create(1L, 3L, "CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS"));

        return items;
    }

    public static ItemEntity create() {
        return ItemEntity.create(1L, 1L, "REVISION DE FUGAS (ACEITE, AGUA, DIESEL)");
    }

}
