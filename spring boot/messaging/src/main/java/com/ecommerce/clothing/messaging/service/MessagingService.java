package com.ecommerce.clothing.messaging.service;

import com.ecommerce.clothing.messaging.constant.AppConstant;
import com.ecommerce.clothing.messaging.model.entity.MessageContentConfig;
import com.ecommerce.clothing.messaging.model.request.messagecontent.DynamicValueRequest;
import com.ecommerce.clothing.messaging.model.response.CommonResponse;
import com.ecommerce.clothing.messaging.repository.MessageContentConfigRepository;
import com.ecommerce.clothing.messaging.tpservice.GmailMessageService;
import com.ecommerce.clothing.messaging.tpservice.TelegramBotSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final MessageContentConfigRepository repository;

    private final TelegramBotSendMessageService telegramBotSendMessageService;

    private final GmailMessageService gmailMessageService;

    public CommonResponse sendTextMessage(String messagingCredentialName, String messagingContentName, String customMessage, DynamicValueRequest dynamicValuesRequest) {

        CommonResponse response = new CommonResponse();
        String text;

        if (Objects.nonNull(customMessage)) {
            text = customMessage;
        } else {

            if (Objects.isNull(messagingContentName)) {
                response.setResponse(AppConstant.FAILED + " content name is empty .... ");
                return response;
            }

            Optional<MessageContentConfig> contentConfigOptional = repository.findByName(messagingContentName);

            if (contentConfigOptional.isEmpty()) {
                response.setResponse(AppConstant.FAILED + " content config is not exists .... ");
                return response;
            }

            MessageContentConfig contentConfig = contentConfigOptional.get();

            if (Objects.isNull(contentConfig.getMessageContent().get("text"))) {
                response.setResponse(AppConstant.FAILED + " content config is required field is missing .... ");
                return response;
            }
            text = contentConfig.getMessageContent().get("text");

            if (Objects.nonNull(dynamicValuesRequest) && Objects.nonNull(dynamicValuesRequest.getDynamicValue())) {
                for (Map.Entry<String, String> entry : dynamicValuesRequest.getDynamicValue().entrySet()) {
                    if (Objects.nonNull(entry.getKey()) && text.contains(entry.getKey()))
                        text = text.replace(entry.getKey(), entry.getValue());
                }
            }

        }
        String messageResult = telegramBotSendMessageService.sendMessage(text, messagingCredentialName);
        response.setResponse(messageResult);
        return response;
    }

    public CommonResponse sendGmail(String messagingCredentialName, String messagingContentName, String to, String subject, String body, DynamicValueRequest dynamicValuesRequest) throws MessagingException {

        CommonResponse response = new CommonResponse();
        String mailSubject;
        String mailBody;

        if (Objects.nonNull(subject) && Objects.nonNull(body)) {
            mailSubject = subject;
            mailBody = body;
        } else {

            if (Objects.isNull(messagingContentName)) {
                response.setResponse(AppConstant.FAILED + " content name is empty .... ");
                return response;
            }

            Optional<MessageContentConfig> contentConfigOptional = repository.findByName(messagingContentName);

            if (contentConfigOptional.isEmpty()) {
                response.setResponse(AppConstant.FAILED + " content config is not exists .... ");
                return response;
            }

            MessageContentConfig contentConfig = contentConfigOptional.get();

            if (Objects.isNull(contentConfig.getMessageContent().get("subject")) || Objects.isNull(contentConfig.getMessageContent().get("body"))) {
                response.setResponse(AppConstant.FAILED + " content config is required field is missing .... ");
                return response;
            }

            mailSubject = contentConfig.getMessageContent().get("subject");
            mailBody = contentConfig.getMessageContent().get("body");

            if (Objects.nonNull(dynamicValuesRequest) && Objects.nonNull(dynamicValuesRequest.getDynamicValue())) {
                for (Map.Entry<String, String> entry : dynamicValuesRequest.getDynamicValue().entrySet()) {
                    if (Objects.nonNull(entry.getKey()) && mailBody.contains(entry.getKey()))
                        mailBody = mailBody.replace(entry.getKey(), entry.getValue());
                }
            }

        }

        String messageResult = gmailMessageService.sendGmail(messagingCredentialName, to, mailSubject, mailBody);
        response.setResponse(messageResult);
        return response;
    }
}
