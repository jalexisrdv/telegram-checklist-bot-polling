package com.jardvcode.bot.user.service;

import com.jardvcode.bot.user.entity.BotSessionDataEntity;
import com.jardvcode.bot.user.repository.BotSessionDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BotSessionDataService {

    private final BotSessionDataRepository repository;

    public BotSessionDataService(BotSessionDataRepository repository) {
        this.repository = repository;
    }

    public BotSessionDataEntity findByPlatformUserId(String platformUserId, String key) {
        return repository.findByPlatformUserIdAndKey(platformUserId, key).orElseThrow();
    }

    public void save(BotSessionDataEntity entity) {
        Optional<BotSessionDataEntity> optional = repository.findByPlatformUserIdAndKey(entity.getPlatformUserId(), entity.getKey());

        if(optional.isEmpty()) {
            repository.save(entity);
            return;
        }

        BotSessionDataEntity entityFound = optional.get();
        entity.setId(entityFound.getId());

        repository.save(entity);
    }

    @Transactional
    public void deleteByPlatformUserId(String platformUserId) {
        repository.deleteByPlatformUserId(platformUserId);
    }
}
