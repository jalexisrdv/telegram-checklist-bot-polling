package com.jardvcode.bot.user.service;

import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import com.jardvcode.bot.user.entity.BotUserEntity;
import com.jardvcode.bot.user.entity.UserLinkTokenEntity;
import com.jardvcode.bot.shared.domain.exception.BotException;
import com.jardvcode.bot.user.repository.UserBotStateRepository;
import com.jardvcode.bot.user.repository.UserLinkTokenRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserLinkTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLinkTokenService.class);

    private final UserLinkTokenRepository userLinkTokenRepository;
    private final UserBotStateRepository userBotRepository;

    public UserLinkTokenService(UserLinkTokenRepository userLinkTokenRepository, UserBotStateRepository userBotRepository) {
        this.userLinkTokenRepository = userLinkTokenRepository;
        this.userBotRepository = userBotRepository;
    }

    @Transactional
    public void linkBotUserToSystemUser(String token, String providerUserId) {
        try {
            UserLinkTokenEntity userLinkTokenEntity = userLinkTokenRepository.findByToken(token).orElseThrow(() -> new BotException("No se pudo encontrar el token de acceso."));

            if (userLinkTokenEntity.getUsed() || userLinkTokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new BotException("El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.");
            }

            BotUserEntity userBotEntity = userBotRepository.findByProviderUserId(providerUserId).orElseThrow(() -> new BotException("Usuario no encontrado."));

            userLinkTokenEntity.setUsed(true);
            userLinkTokenRepository.save(userLinkTokenEntity);

            userBotEntity.setUserId(userLinkTokenEntity.getUserId());
            userBotRepository.save(userBotEntity);
        } catch(BotException e) {
            throw e;
        } catch(Exception e) {
            LOGGER.error("Unexpected error processing token for providerUserId={}", providerUserId, e);
            throw new UnexpectedException();
        }
    }

}