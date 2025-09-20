package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import com.jardvcode.bot.checklist.repository.instance.ResponseRepository;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ItemResponseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemResponseService.class);

    private final ResponseRepository repository;

    public ItemResponseService(ResponseRepository repository) {
        this.repository = repository;
    }

    public List<ResponseEntity> findByInstanceIdAndGroupId(Long checklistId, Long groupId) {
        try {
            return repository.findByInstanceIdAndGroupIdOrderByOptionNumberAsc(checklistId, groupId);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving responses for checklistId={} and groupId={}", checklistId, groupId, e);
            throw new UnexpectedException();
        }
    }

    public ResponseEntity findByInstanceIdAndGroupIdAndOptionNumber(Long checklistId, Long groupId, Long optionNumber) {
        try {
            return repository.findByInstanceIdAndGroupIdAndOptionNumber(checklistId, groupId, optionNumber).orElseThrow(() -> new DataNotFoundException());
        } catch (DataNotFoundException e) {
            LOGGER.error("Response not found for checklistId={} groupId={} optionNumber={}", checklistId, groupId, optionNumber, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving response for checklistId={} groupId={} optionNumber={}", checklistId, groupId, optionNumber, e);
            throw new UnexpectedException();
        }
    }

    public void save(Long id, String status, String observation) {
        try {
            ResponseEntity entityFound = repository.findById(id).orElseThrow(() -> new DataNotFoundException());

            entityFound.setId(id);
            entityFound.setStatus(status);
            entityFound.setObservation(observation);

            repository.save(entityFound);
        } catch (DataNotFoundException e) {
            LOGGER.error("Response not found for id={}", id, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while persisting response for id={}", id, e);
            throw new UnexpectedException();
        }
    }

}
