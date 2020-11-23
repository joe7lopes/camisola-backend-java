package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.out.EmailNotifier;
import com.camisola10.camisolabackend.domain.events.OrderCreatedEvent;
import com.camisola10.camisolabackend.domain.events.OrderStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
class EmailNotificationService {

    private final EmailNotifier emailNotifier;

    @Async
    @EventListener
    public void handleEvent(OrderCreatedEvent event) {
        var emailAddress = event.getOrder().getShippingAddress().getEmailAddress();
        if (isNull(emailAddress)) {
            log.info("{} email address is null, skipping email notification ", this.getClass().getSimpleName());
            return;
        }
        emailNotifier.send(event);
    }


    @Async
    @EventListener
    public void handleEvent(OrderStatusUpdatedEvent event) {
        log.info("Sending notification {}", event);
        //TODO
    }

}
