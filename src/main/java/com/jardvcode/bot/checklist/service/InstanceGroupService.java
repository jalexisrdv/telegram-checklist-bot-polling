package com.jardvcode.bot.checklist.service;

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

    public List<InstanceGroupEntity> findByAssignmentId(Long assignmentId) {
        try {
            return repository.findByAssignmentIdOrderByOptionNumberAsc(assignmentId);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving section for assignmentId={}", assignmentId, e);
            throw new UnexpectedException();
        }
    }

    public InstanceGroupEntity findByAssignmentIdAndOptionNumber(Long assignmentId, Long optionNumber) {
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
