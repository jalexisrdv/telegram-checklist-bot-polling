package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.repository.instance.InstanceRepository;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class InstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceService.class);

    private final InstanceRepository repository;

    public InstanceService(InstanceRepository repository) {
        this.repository = repository;
    }

    public List<InstanceEntity> findUnconfirmedByUserId(Long userId) {
        try {
            return repository.findByUserIdAndStatusNotOrderByDateDesc(userId, ChecklistStatusEmoji.CONFIRMADO.name());
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving checklists for userId={}", userId, e);
            throw new UnexpectedException();
        }
    }

    public InstanceEntity findByUserIdAndOptionNumber(Long userId, Long optionNumber) {
        try {
            return repository.findByUserIdAndOptionNumber(userId, optionNumber).orElseThrow(() -> new DataNotFoundException());
        } catch (DataNotFoundException e) {
            LOGGER.error("Checklist not found for userId={} optionNumber={}", userId, optionNumber, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving checklist for userId={} optionNumber={}", userId, optionNumber, e);
            throw new UnexpectedException();
        }
    }

    public void markAsCompleted(Long checklistId) {
        try {
            InstanceEntity entityFound = repository.findById(checklistId).orElseThrow(() -> new DataNotFoundException());
            entityFound.setStatus(ChecklistStatusEmoji.COMPLETADO.name());
            repository.save(entityFound);
        } catch (DataNotFoundException e) {
            LOGGER.error("Checklist not found for id={}", checklistId, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while persisting checklist for id={}", checklistId, e);
            throw new UnexpectedException();
        }
    }

}
