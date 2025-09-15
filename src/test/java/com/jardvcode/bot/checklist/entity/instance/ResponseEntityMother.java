package com.jardvcode.bot.checklist.entity.instance;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.entity.template.ItemEntity;
import com.jardvcode.bot.checklist.entity.template.ItemEntityMother;

import java.util.ArrayList;
import java.util.List;

public final class ResponseEntityMother {

    public static ArrayList<ResponseEntity> withCompletedStatus() {
        ArrayList<ResponseEntity> instanceItems = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.values();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Long optionNumber = Long.valueOf(i + 1);
            String status = ChecklistStatusEmoji.COMPLETADO.name();
            instanceItems.add(withItem(item, optionNumber, status));
        }

        return instanceItems;
    }

    public static ArrayList<ResponseEntity> withRandomStatus() {
        ArrayList<ResponseEntity> instanceItems = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.values();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Long optionNumber = Long.valueOf(i + 1);
            String status = optionNumber % 2 == 0 ? "OK" : null;
            instanceItems.add(withItem(item, optionNumber, status));
        }

        return instanceItems;
    }

    public static ResponseEntity withPendingItem() {
        return withItem(ItemEntityMother.create(), 1L, ChecklistStatusEmoji.PENDIENTE.name());
    }

    private static ResponseEntity withItem(ItemEntity item, Long optionNumber, String status) {
        ResponseEntity responseEntity = new ResponseEntity();

        responseEntity.setId(1L);
        responseEntity.setInstanceId(1L);
        responseEntity.setItem(item);
        responseEntity.setOptionNumber(optionNumber);
        responseEntity.setStatus(status);
        responseEntity.setObservation("");

        return responseEntity;
    }

}
