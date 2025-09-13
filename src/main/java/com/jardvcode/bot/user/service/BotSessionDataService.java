package com.jardvcode.bot.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import com.jardvcode.bot.shared.util.JsonUtils;
import com.jardvcode.bot.user.entity.BotSessionDataEntity;
import com.jardvcode.bot.user.repository.BotSessionDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BotSessionDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotSessionDataService.class);

    private final BotSessionDataRepository repository;

    public BotSessionDataService(BotSessionDataRepository repository) {
        this.repository = repository;
    }

    public <T> T findByPlatformUserId(String platformUserId, String key, Class<T> toClass) {
        try {
            BotSessionDataEntity data = repository.findByPlatformUserIdAndKey(platformUserId, key).orElseThrow(() -> new DataNotFoundException());

            return JsonUtils.decode(data.getValue(), toClass);
        } catch (DataNotFoundException e) {
            LOGGER.error("Session data not found for userId={} key={}", platformUserId, key, e);
            throw e;
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to deserialize session data for userId={} key={}", platformUserId, key, e);
            throw new UnexpectedException();
        } catch (Exception e) {
            LOGGER.error("Unexpected error processing session data for userId={} key={}", platformUserId, key, e);
            throw new UnexpectedException();
        }
    }

    public void save(BotSessionDataEntity entity) {
        try {
            Optional<BotSessionDataEntity> optional = repository.findByPlatformUserIdAndKey(entity.getPlatformUserId(), entity.getKey());

            if(optional.isEmpty()) {
                repository.save(entity);
                return;
            }

            BotSessionDataEntity entityFound = optional.get();
            entity.setId(entityFound.getId());

            repository.save(entity);
        } catch (Exception e) {
            LOGGER.error("Failed to save session data for userId={} key={}", entity.getPlatformUserId(), entity.getKey(), e);
            throw new UnexpectedException();
        }
    }

    @Transactional
    public void deleteByPlatformUserId(String platformUserId) {
        try {
            repository.deleteByPlatformUserId(platformUserId);
        } catch (Exception e) {
            LOGGER.error("Failed to delete session data for userId={}", platformUserId, e);
            throw new UnexpectedException();
        }
    }
}
