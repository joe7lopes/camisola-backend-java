package com.camisola10.camisolabackend.domain.notification;

import com.camisola10.camisolabackend.domain.EmailAddress;
import lombok.Value;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Value
public class EmailNotification {

    String from = "CAMISOLA10";
    EmailAddress to;
    String subject;
    String message;
    LocalDateTime sentAt;

    public EmailNotification(EmailAddress to, String subject, String message, LocalDateTime sentAt) {
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.sentAt = sentAt;
        validate();
    }

    private void validate() {
        if (isNull(to) || isNull(subject) || isNull(message) || isNull(sentAt)) {
            throw new InvalidEmailNotification(this);
        }
    }

    private static class InvalidEmailNotification extends RuntimeException {
        public InvalidEmailNotification(EmailNotification notification) {
            super(String.format("Invalid notification: %s", notification));
        }
    }
}
