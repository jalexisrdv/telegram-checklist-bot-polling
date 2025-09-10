package com.jardvcode.bot.checklist.service;

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

    public void update(InstanceGroupEntity entity) {
        InstanceGroupEntity entityFound = repository.findByInstanceIdAndOptionNumber(entity.getInstanceId(), entity.getOptionNumber()).orElseThrow();
        entityFound.setStatus(entity.getStatus());
        repository.save(entityFound);
    }

}
