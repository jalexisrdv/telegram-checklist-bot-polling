package com.jardvcode.bot.checklist.entity.instance;

import com.jardvcode.bot.checklist.entity.template.ItemEntity;
import com.jardvcode.bot.checklist.entity.template.ItemEntityMother;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ResponseEntityMother {

    private final static String[] ESTATUSES = {"F", "OK", "R"};
    private final static String[] OBSERVATIONS = {"OBSERVATION 1", "OBSERVATION 2", "OBSERVATION 3"};

    public static ArrayList<ResponseEntity> checklistResponses() {
        ArrayList<ResponseEntity> instanceItems = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.checklistItems();

        Random random = new Random();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Long optionNumber = Long.valueOf(i + 1);

            int randomIndex = random.nextInt(ESTATUSES.length);
            String status = ESTATUSES[randomIndex];
            String observation = OBSERVATIONS[randomIndex];

            instanceItems.add(withItem(item, optionNumber, status, observation));
        }

        return instanceItems;
    }

    public static ArrayList<ResponseEntity> withCompletedStatus() {
        ArrayList<ResponseEntity> instanceItems = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.motorItems();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Long optionNumber = Long.valueOf(i + 1);
            instanceItems.add(withItem(item, optionNumber, "OK", "OBSERVATION"));
        }

        return instanceItems;
    }

    public static ArrayList<ResponseEntity> withSomeResponses() {
        ArrayList<ResponseEntity> instanceItems = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.motorItems();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Long optionNumber = Long.valueOf(i + 1);

            int result = i % 2;
            String status = result == 0 ? "OK" : null;
            String observation = result == 0 ? "OBSERVATION" : null;

            instanceItems.add(withItem(item, optionNumber, status, observation));
        }

        return instanceItems;
    }

    public static ResponseEntity withPendingItem() {
        return withItem(ItemEntityMother.motorItem(), 1L, "", "");
    }

    private static ResponseEntity withItem(ItemEntity item, Long optionNumber, String status, String observation) {
        ResponseEntity responseEntity = new ResponseEntity();

        responseEntity.setId(1L);
        responseEntity.setInstanceId(1L);
        responseEntity.setItem(item);
        responseEntity.setOptionNumber(optionNumber);
        responseEntity.setStatus(status);
        responseEntity.setObservation(observation);

        return responseEntity;
    }

}
