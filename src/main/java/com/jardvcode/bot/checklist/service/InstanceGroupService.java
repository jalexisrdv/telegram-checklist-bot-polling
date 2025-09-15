package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import com.jardvcode.bot.checklist.repository.instance.InstanceGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class InstanceGroupService {

    private final InstanceGroupRepository repository;

    public InstanceGroupService(InstanceGroupRepository repository) {
        this.repository = repository;
    }

    public List<InstanceGroupEntity> findByInstanceId(Long id) {
        return repository.findByInstanceIdOrderByOptionNumberAsc(id);
    }

    public InstanceGroupEntity findByInstanceIdAndOptionNumber(Long instanceId, Long userGroupNumber) {
        return repository.findByInstanceIdAndOptionNumber(instanceId, userGroupNumber).orElseThrow();
    }

    public void markAsCompleted(Long instanceId, Long groupId) {
        InstanceGroupEntity entityFound = repository.findByInstanceIdAndOptionNumber(instanceId, groupId).orElseThrow();
        entityFound.setStatus(ChecklistStatusEmoji.COMPLETADO.name());
        repository.save(entityFound);
    }

}
