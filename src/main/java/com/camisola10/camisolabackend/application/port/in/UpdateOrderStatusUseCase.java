package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;

public interface UpdateOrderStatusUseCase {

    void updateOrderStatus(UpdateOrderStatusCommand command);
}
