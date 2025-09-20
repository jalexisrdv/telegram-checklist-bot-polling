package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.domain.ChecklistStatusEmoji;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import com.jardvcode.bot.checklist.repository.instance.InstanceGroupRepository;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class InstanceGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceGroupService.class);

    private final InstanceGroupRepository repository;

    public InstanceGroupService(InstanceGroupRepository repository) {
        this.repository = repository;
    }

    public List<InstanceGroupEntity> findByInstanceId(Long checklistId) {
        try {
            return repository.findByInstanceIdOrderByOptionNumberAsc(checklistId);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving group for checklistId={}", checklistId, e);
            throw new UnexpectedException();
        }
    }

    public InstanceGroupEntity findByInstanceIdAndOptionNumber(Long checklistId, Long optionNumber) {
        try {
            return repository.findByInstanceIdAndOptionNumber(checklistId, optionNumber).orElseThrow();
        } catch (DataNotFoundException e) {
            LOGGER.error("Group not found for checklistId={} optionNumber={}", checklistId, optionNumber, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving group for checklistId={} optionNumber={}", checklistId, optionNumber, e);
            throw new UnexpectedException();
        }
    }

    public void markAsCompleted(Long checklistId, Long groupId) {
        try {
            InstanceGroupEntity entityFound = repository.findByInstanceIdAndOptionNumber(checklistId, groupId).orElseThrow();
            entityFound.setStatus(ChecklistStatusEmoji.COMPLETADO.name());
            repository.save(entityFound);
        } catch (DataNotFoundException e) {
            LOGGER.error("Group not found for checklistId={} groupId={}", checklistId, groupId, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while persisting group for checklistId={} groupId={}", checklistId, groupId, e);
            throw new UnexpectedException();
        }
    }

}
