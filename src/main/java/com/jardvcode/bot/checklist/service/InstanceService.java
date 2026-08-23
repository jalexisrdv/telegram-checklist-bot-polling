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

    public List<InstanceEntity> findUnconfirmedByMechanicUserId(Long mechanicUserId) {
        try {
            return repository.findByMechanicUserIdAndStatusNotOrderByDateDesc(mechanicUserId, ChecklistStatusEmoji.CONFIRMADO.name());
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving assignments for mechanicUserId={}", mechanicUserId, e);
            throw new UnexpectedException();
        }
    }

    public InstanceEntity findByMechanicUserIdAndOptionNumber(Long mechanicUserId, Long optionNumber) {
        try {
            return repository.findByMechanicUserIdAndOptionNumber(mechanicUserId, optionNumber).orElseThrow(() -> new DataNotFoundException());
        } catch (DataNotFoundException e) {
            LOGGER.error("Assignment not found for mechanicUserId={} optionNumber={}", mechanicUserId, optionNumber, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving assignment for mechanicUserId={} optionNumber={}", mechanicUserId, optionNumber, e);
            throw new UnexpectedException();
        }
    }

}
