package com.camisola10.camisolabackend.notification;

import com.camisola10.camisolabackend.application.port.out.EmailNotifier;
import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("email")
class EmailNotifierImpl implements EmailNotifier {

    private final JavaMailSender emailSender;
    private final EmailConverter converter;

    @Override
    public void send(OrderCreatedEvent event) {

        Email email = converter.convert(event);
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(email.getFrom());
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessage(), true);
        } catch (MessagingException e) {
            log.error("Unable to send email {}", message, e);
        }

        emailSender.send(message);
        log.info("Sent notification email {}", email);

    }

}
