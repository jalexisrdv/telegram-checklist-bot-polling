package com.jardvcode.bot.checklist.entity.instance;

import com.jardvcode.bot.checklist.entity.template.ItemEntityMother;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ResponseEntityMother {

    private final static String[] ESTATUSES = {"F", "OK", "R"};
    private final static String[] OBSERVATIONS = {"OBSERVATION 1", "OBSERVATION 2", "OBSERVATION 3"};

    public static ArrayList<ResponseEntity> responses() {
        ArrayList<ResponseEntity> responses = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.checklistItems();

        Random random = new Random();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Long optionNumber = Long.valueOf(i + 1);

            int randomIndex = random.nextInt(ESTATUSES.length);
            String status = ESTATUSES[randomIndex];
            String comment = OBSERVATIONS[randomIndex];

            responses.add(withItem(item, status, comment));
        }

        return responses;
    }

    public static ArrayList<ResponseEntity> withCompletedStatus() {
        ArrayList<ResponseEntity> responses = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.motorItems();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Long optionNumber = Long.valueOf(i + 1);
            responses.add(withItem(item, "OK", "OBSERVATION"));
        }

        return responses;
    }

    public static ArrayList<ResponseEntity> withSomeResponses() {
        ArrayList<ResponseEntity> responses = new ArrayList<>();
        List<ItemEntity> items = ItemEntityMother.motorItems();

        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);

            int result = i % 2;
            String status = result == 0 ? "OK" : null;
            String commit = result == 0 ? "OBSERVATION" : null;

            responses.add(withItem(item, status, commit));
        }

        return responses;
    }

    public static ResponseEntity withPendingItem() {
        return withItem(ItemEntityMother.motorItem(), "", "");
    }

    private static ResponseEntity withItem(ItemEntity item, String status, String commit) {
        ResponseEntity response = new ResponseEntity();

        response.setId(1L);
        response.setAssignmentId(1L);
        response.setItem(item);
        response.setStatus(status);
        response.setComment(commit);

        return response;
    }

}
