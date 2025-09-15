package com.jardvcode.bot.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.shared.domain.state.StateUtil;
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

    public <T extends Record> T findByUserId(Long userId, Class<T> dto) {
        String key = dto.getSimpleName();

        try {
            BotSessionDataEntity data = repository.findByUserIdAndKey(userId, key).orElseThrow(() -> new DataNotFoundException());

            return JsonUtils.decode(data.getValue(), dto);
        } catch (DataNotFoundException e) {
            LOGGER.error("Session data not found for userId={} key={}", userId, key, e);
            throw e;
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to deserialize session data for userId={} key={}", userId, key, e);
            throw new UnexpectedException();
        } catch (Exception e) {
            LOGGER.error("Unexpected error processing session data for userId={} key={}", userId, key, e);
            throw new UnexpectedException();
        }
    }

    public <T extends Record> void save(Long userId, T dto, Class<? extends State> state) {
        String key = dto.getClass().getSimpleName();

        try {
            BotSessionDataEntity entity = BotSessionDataEntity.create(
                    userId,
                    StateUtil.uniqueName(state),
                    key,
                    JsonUtils.encode(dto)
            );

            Optional<BotSessionDataEntity> optional = repository.findByUserIdAndKey(userId, key);

            if(optional.isEmpty()) {
                repository.save(entity);
                return;
            }

            BotSessionDataEntity entityFound = optional.get();
            entity.setId(entityFound.getId());

            repository.save(entity);
        } catch (Exception e) {
            LOGGER.error("Failed to save session data for userId={} key={}", userId, key, e);
            throw new UnexpectedException();
        }
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        try {
            repository.deleteByUserId(userId);
        } catch (Exception e) {
            LOGGER.error("Failed to delete session data for userId={}", userId, e);
            throw new UnexpectedException();
        }
    }
}
