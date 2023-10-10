package com.ecommerce.clothing.messaging.config;

import com.ecommerce.clothing.messaging.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@Configuration
public class TelegramBotAutoChatConfig extends TelegramLongPollingBot {   /* on holding auto chat_bot logic */

    @Override
    public String getBotUsername() {
        return AppConstant.TELEGRAM_BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return AppConstant.TELEGRAM_BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            log.info("incoming message : [{}]", update.getMessage());
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            String text;
            if (message.hasText())
                text = "received from : " + message.getText();
            else
                text = "i am not getting test message from your side , plz send a text format message ... ";

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("response :  " + text);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("telegram update and send reply getting exception : [{}] ", e.getMessage());
            }
        }
    }
}

