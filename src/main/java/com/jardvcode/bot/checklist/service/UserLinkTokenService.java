package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.shared.domain.entity.UserBotStateEntity;
import com.jardvcode.bot.shared.domain.entity.UserLinkTokenEntity;
import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.shared.domain.repository.UserBotStateRepository;
import com.jardvcode.bot.shared.domain.repository.UserLinkTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserLinkTokenService {

    private final UserLinkTokenRepository userLinkTokenRepository;
    private final UserBotStateRepository userBotRepository;

    public UserLinkTokenService(UserLinkTokenRepository userLinkTokenRepository, UserBotStateRepository userBotRepository) {
        this.userLinkTokenRepository = userLinkTokenRepository;
        this.userBotRepository = userBotRepository;
    }

    @Transactional
    public void linkUser(String token, String platformUserId) {
        UserLinkTokenEntity userLinkTokenEntity = userLinkTokenRepository.findByToken(token).orElseThrow(() -> new BotException("No se pudo encontrar el token de acceso."));

        if (userLinkTokenEntity.getUsed() || userLinkTokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BotException("El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.");
        }

        UserBotStateEntity userBotEntity = userBotRepository.findByPlatformUserId(platformUserId).orElseThrow(() -> new BotException("No se pudo encontrar el usuario."));

        userLinkTokenEntity.setUsed(true);
        userLinkTokenRepository.save(userLinkTokenEntity);

        userBotEntity.setUserId(userLinkTokenEntity.getUserId());
        userBotRepository.save(userBotEntity);
    }

}