package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.entity.ResponseEntity;
import com.jardvcode.bot.checklist.repository.ResponseRepository;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ResponseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseService.class);

    private final ResponseRepository repository;

    public ResponseService(ResponseRepository repository) {
        this.repository = repository;
    }

    public List<ResponseEntity> findByAssignmentIdAndSectionId(Long assignmentId, Long sectionId) {
        try {
            return repository.findByAssignmentIdAndItemSectionIdOrderByItemOptionNumberAsc(assignmentId, sectionId);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving responses for assignmentId={} and sectionId={}", assignmentId, sectionId, e);
            throw new UnexpectedException();
        }
    }

    public ResponseEntity findByAssignmentIdAndSectionIdAndOptionNumber(Long assignmentId, Long sectionId, Long optionNumber) {
        try {
            return repository.findByAssignmentIdAndItemSectionIdAndItemOptionNumber(assignmentId, sectionId, optionNumber).orElseThrow(() -> new DataNotFoundException());
        } catch (DataNotFoundException e) {
            LOGGER.error("Response not found for assignmentId={} sectionId={} optionNumber={}", assignmentId, sectionId, optionNumber, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving response for assignmentId={} sectionId={} optionNumber={}", assignmentId, sectionId, optionNumber, e);
            throw new UnexpectedException();
        }
    }

    public void save(Long id, String status, String comment) {
        try {
            ResponseEntity responseFound = repository.findById(id).orElseThrow(() -> new DataNotFoundException());

            responseFound.update(status, comment);

            repository.save(responseFound);
        } catch (DataNotFoundException e) {
            LOGGER.error("Response not found for id={}", id, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while persisting response for id={}", id, e);
            throw new UnexpectedException();
        }
    }

}
