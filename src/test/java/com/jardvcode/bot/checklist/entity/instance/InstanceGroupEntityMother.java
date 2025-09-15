package com.jardvcode.bot.checklist.entity.instance;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.entity.template.GroupEntity;
import com.jardvcode.bot.checklist.entity.template.GroupEntityMother;

import java.util.ArrayList;
import java.util.List;

public final class InstanceGroupEntityMother {

    public static ArrayList<InstanceGroupEntity> withCompletedStatus() {
        ArrayList<InstanceGroupEntity> instanceGroups = new ArrayList<>();
        List<GroupEntity> groups = GroupEntityMother.values();

        for (int i = 0; i < groups.size(); i++) {
            GroupEntity group = groups.get(i);
            Long optionNumber = Long.valueOf(i + 1);
            String status = ChecklistStatusEmoji.COMPLETADO.name();
            instanceGroups.add(withGroup(group, optionNumber, status));
        }

        return instanceGroups;
    }

    public static ArrayList<InstanceGroupEntity> withRandomStatus() {
        ArrayList<InstanceGroupEntity> instanceGroups = new ArrayList<>();
        List<GroupEntity> groups = GroupEntityMother.values();

        for (int i = 0; i < groups.size(); i++) {
            GroupEntity group = groups.get(i);
            Long optionNumber = Long.valueOf(i + 1);
            String status = optionNumber % 2 == 0 ? ChecklistStatusEmoji.PENDIENTE.name() : ChecklistStatusEmoji.COMPLETADO.name();
            instanceGroups.add(withGroup(group, optionNumber, status));
        }

        return instanceGroups;
    }

    public static InstanceGroupEntity withPendingGroup() {
        return withGroup(GroupEntityMother.create(), 1L, ChecklistStatusEmoji.PENDIENTE.name());
    }

    private static InstanceGroupEntity withGroup(GroupEntity group, Long optionNumber, String status) {
        InstanceGroupEntity instanceGroup = new InstanceGroupEntity();

        instanceGroup.setId(1L);
        instanceGroup.setInstanceId(1L);
        instanceGroup.setGroup(group);
        instanceGroup.setOptionNumber(optionNumber);
        instanceGroup.setStatus(status);

        return instanceGroup;
    }

}
