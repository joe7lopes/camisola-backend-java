package com.camisola10.camisolabackend.notification;

import com.camisola10.camisolabackend.application.port.out.EmailNotifier;
import com.camisola10.camisolabackend.domain.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!email")
@Slf4j
public class EmailNotifierMock implements EmailNotifier {

    @Override
    public void send(OrderCreatedEvent event) {
      log.info("Email sent {}", event);
    }
}
