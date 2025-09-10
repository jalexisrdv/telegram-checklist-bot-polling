package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import com.jardvcode.bot.checklist.repository.instance.ResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ItemResponseService {

    private final ResponseRepository repository;

    public ItemResponseService(ResponseRepository repository) {
        this.repository = repository;
    }

    public List<ResponseEntity> findByInstanceIdAndGroupId(Long instanceId, Long groupId) {
        return repository.findByInstanceIdAndGroupIdOrderByOptionNumberAsc(instanceId, groupId);
    }

    public ResponseEntity findByInstanceIdAndGroupIdAndOptionNumber(Long instanceId, Long groupId, Long userItemNumber) {
        return repository.findByInstanceIdAndGroupIdAndOptionNumber(instanceId, groupId, userItemNumber).orElseThrow();
    }

    public void save(ResponseEntity entity) {
        ResponseEntity entityFound = repository.findById(entity.getId()).orElseThrow();

        entityFound.setId(entity.getId());
        entityFound.setStatus(entity.getStatus());
        entityFound.setObservation(entity.getObservation());

        repository.save(entityFound);
    }

}
