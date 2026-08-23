package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.entity.SectionViewEntity;
import com.jardvcode.bot.checklist.repository.SectionRepository;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SectionService.class);

    private final SectionRepository repository;

    public SectionService(SectionRepository repository) {
        this.repository = repository;
    }

    public List<SectionViewEntity> findByAssignmentId(Long assignmentId) {
        try {
            return repository.findByAssignmentIdOrderByOptionNumberAsc(assignmentId);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving section for assignmentId={}", assignmentId, e);
            throw new UnexpectedException();
        }
    }

    public SectionViewEntity findByAssignmentIdAndOptionNumber(Long assignmentId, Long optionNumber) {
        try {
            return repository.findByAssignmentIdAndOptionNumber(assignmentId, optionNumber).orElseThrow();
        } catch (DataNotFoundException e) {
            LOGGER.error("Section not found for assignmentId={} optionNumber={}", assignmentId, optionNumber, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving section for assignmentId={} optionNumber={}", assignmentId, optionNumber, e);
            throw new UnexpectedException();
        }
    }

}
