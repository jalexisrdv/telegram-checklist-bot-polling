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

@Service
public class UserLinkTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLinkTokenService.class);

    private final UserLinkTokenRepository botActivationTokenRepository;
    private final UserBotStateRepository botUserRepository;

    public UserLinkTokenService(UserLinkTokenRepository userLinkTokenRepository, UserBotStateRepository userBotRepository) {
        this.botActivationTokenRepository = userLinkTokenRepository;
        this.botUserRepository = userBotRepository;
    }

    @Transactional
    public void linkBotToErpUser(String token, String providerUserId) {
        try {
            UserLinkTokenEntity botActivationToken = botActivationTokenRepository.findByToken(token).orElseThrow(() -> new BotException("No se pudo encontrar el token de acceso."));

            if (!botActivationToken.isValidToken()) {
                throw new BotException("El token ingresado no es válido o ya expiró. Solicita uno nuevo si es necesario.");
            }

            BotUserEntity botUser = botUserRepository.findByProviderUserId(providerUserId).orElseThrow(() -> new BotException("Usuario no encontrado."));

            botActivationToken.markAsUsed();
            botActivationTokenRepository.save(botActivationToken);

            botUser.linkToErpUser(botActivationToken.getUserId());
            botUserRepository.save(botUser);
        } catch(BotException e) {
            throw e;
        } catch(Exception e) {
            LOGGER.error("Unexpected error processing token for providerUserId={}", providerUserId, e);
            throw new UnexpectedException();
        }
    }

}