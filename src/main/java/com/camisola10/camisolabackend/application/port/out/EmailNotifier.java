package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.events.OrderCreatedEvent;

public interface EmailNotifier {

    void send(OrderCreatedEvent event);
}
