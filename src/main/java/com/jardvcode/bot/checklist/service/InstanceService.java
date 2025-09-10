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

    public void update(InstanceEntity entity) {
        InstanceEntity entityFound = repository.findById(entity.getId()).orElseThrow();
        entityFound.setStatus(entity.getStatus());
        repository.save(entityFound);
    }

}
