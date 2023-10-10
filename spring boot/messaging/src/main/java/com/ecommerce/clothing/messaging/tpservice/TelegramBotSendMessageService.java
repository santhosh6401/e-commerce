package com.ecommerce.clothing.messaging.tpservice;

import com.ecommerce.clothing.messaging.model.entity.MessageLedger;
import com.ecommerce.clothing.messaging.model.entity.MessagingPartyCredentialsConfig;
import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import com.ecommerce.clothing.messaging.repository.MessageLedgerRepository;
import com.ecommerce.clothing.messaging.repository.MessagingPartyCredentialsConfigRepository;
import com.ecommerce.clothing.messaging.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.ecommerce.clothing.messaging.constant.AppConstant.*;

@Service
@RequiredArgsConstructor
public class TelegramBotSendMessageService {
    private final HelperUtils utils;

    private final RestTemplate restTemplate = new RestTemplate();

    private final MessagingPartyCredentialsConfigRepository credentialsConfigRepository;

    private final MessageLedgerRepository messageLedgerRepository;

    public String sendMessage(String text, String credentialName) {

        Optional<MessagingPartyCredentialsConfig> configOptional = credentialsConfigRepository.findByNameAndMessagingChannel(credentialName, MessagingChannel.TELEGRAM);

        if (configOptional.isEmpty())
            throw new RuntimeException("credentials not found");

        MessagingPartyCredentialsConfig credentialsConfig = configOptional.get();

        if (Objects.isNull(credentialsConfig.getCredentials()) ||
                Objects.isNull(credentialsConfig.getCredentials().get("botUserName")) ||
                Objects.isNull(credentialsConfig.getCredentials().get("botToken")) ||
                Objects.isNull(credentialsConfig.getCredentials().get("chatId")))
            throw new RuntimeException("chatId | username | botToken is missing in credentials config");

        String apiUrl = String.format(TELEGRAM_SEND_MESSAGE_URL, credentialsConfig.getCredentials().get("botToken"), credentialsConfig.getCredentials().get("chatId"), text);

        MessageLedger messageLedger = new MessageLedger();
        messageLedger.setHistoryId(utils.generateId("TL"));
        messageLedger.setReceiverId(credentialsConfig.getCredentials().get("chatId"));
        messageLedger.setMessageContent(String.format("sender : %s,\ntext : %s\n", credentialsConfig.getCredentials().get("chatId"), text));
        messageLedger.setCreatedOn(LocalDateTime.now());

        try {
            restTemplate.getForObject(apiUrl, String.class);
            messageLedger.setStatus(SUCCESS);
            messageLedgerRepository.save(messageLedger);
            return SUCCESS;
        } catch (Exception e) {
            messageLedger.setStatus(FAILED);
            messageLedgerRepository.save(messageLedger);
            return FAILED + " " + e.getMessage();
        }

    }
}
