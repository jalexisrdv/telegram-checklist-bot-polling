package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.domain.ChecklistStatus;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.repository.instance.InstanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class InstanceService {

    private final InstanceRepository repository;

    public InstanceService(InstanceRepository repository) {
        this.repository = repository;
    }

    public List<InstanceEntity> findUnconfirmedByUserId(Long userId) {
        return repository.findByUserIdAndStatusNotOrderByDateDesc(userId, ChecklistStatus.CONFIRMADO.name());
    }

    public InstanceEntity findByUserIdAndOptionNumber(Long userId, Long userInstanceNumber) {
        return repository.findByUserIdAndOptionNumber(userId, userInstanceNumber).orElseThrow();
    }

    public void markAsCompleted(Long instanceId) {
        InstanceEntity entityFound = repository.findById(instanceId).orElseThrow();
        entityFound.setStatus(ChecklistStatus.COMPLETADO.name());
        repository.save(entityFound);
    }

}
