package com.ecommerce.clothing.messaging.tpservice;

import com.ecommerce.clothing.messaging.constant.AppConstant;
import com.ecommerce.clothing.messaging.model.entity.MessageLedger;
import com.ecommerce.clothing.messaging.model.entity.MessagingPartyCredentialsConfig;
import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import com.ecommerce.clothing.messaging.repository.MessageLedgerRepository;
import com.ecommerce.clothing.messaging.repository.MessagingPartyCredentialsConfigRepository;
import com.ecommerce.clothing.messaging.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class GmailMessageService {

    private final HelperUtils utils;

    private final MessagingPartyCredentialsConfigRepository credentialsRepository;

    private final MessageLedgerRepository messageLedgerRepository;

    public String sendGmail(String credentialName, String to, String subject, String body) throws MessagingException {

        if (Objects.isNull(to) || Objects.isNull(subject) || Objects.isNull(body))
            throw new MessagingException("to | subject | body is empty");

        Optional<MessagingPartyCredentialsConfig> credentialsOptional = credentialsRepository.findByNameAndMessagingChannel(credentialName, MessagingChannel.MAIL);

        if (credentialsOptional.isEmpty())
            throw new MessagingException("credentials not found");

        MessagingPartyCredentialsConfig credentialsConfig = credentialsOptional.get();

        if (credentialsConfig.getCredentials().isEmpty())
            throw new MessagingException("credentials is missing");


        if (Objects.isNull(credentialsConfig.getCredentials()) ||
                Objects.isNull(credentialsConfig.getCredentials().get("host")) ||
                Objects.isNull(credentialsConfig.getCredentials().get("port")) ||
                Objects.isNull(credentialsConfig.getCredentials().get("userName")) ||
                Objects.isNull(credentialsConfig.getCredentials().get("password")) ||
                Objects.isNull(credentialsConfig.getCredentials().get("auth")) ||
                Objects.isNull(credentialsConfig.getCredentials().get("starttls"))) {
            throw new MessagingException("credentials is host | port | sender - email | password | auth | starttls is missing in config");
        }
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", credentialsConfig.getCredentials().get("auth"));
        properties.setProperty("mail.smtp.starttls.enable", credentialsConfig.getCredentials().get("starttls"));

        mailSender.setHost(credentialsConfig.getCredentials().get("host"));
        mailSender.setPort(Integer.parseInt(credentialsConfig.getCredentials().get("port")));
        mailSender.setUsername(credentialsConfig.getCredentials().get("userName"));
        mailSender.setPassword(credentialsConfig.getCredentials().get("password"));
        mailSender.setJavaMailProperties(properties);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(subject);
        helper.setFrom(credentialsConfig.getCredentials().get("userName"));
        helper.setTo(to);
        boolean html = true;
        helper.setText(body, html);

        /* message ledger adding*/


        MessageLedger messageLedger = new MessageLedger();
        messageLedger.setHistoryId(utils.generateId("GL"));
        messageLedger.setReceiverId(to);
        messageLedger.setMessageContent(String.format("to : %s,\nsubject : %s,\nbody : %s\n", to, subject, body));
        messageLedger.setCreatedOn(LocalDateTime.now());
        try {
            mailSender.send(message);
            messageLedger.setStatus(AppConstant.SUCCESS);
            messageLedgerRepository.save(messageLedger);
            return AppConstant.SUCCESS;
        } catch (Exception ex) {
            messageLedger.setStatus(AppConstant.FAILED);
            messageLedgerRepository.save(messageLedger);
            return AppConstant.FAILED + " " + ex.getMessage();
        }
    }
}
