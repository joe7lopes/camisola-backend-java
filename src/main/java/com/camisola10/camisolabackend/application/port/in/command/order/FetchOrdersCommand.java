package com.camisola10.camisolabackend.application.port.in.command.order;

import com.camisola10.camisolabackend.domain.order.Order.Status;
import lombok.Data;

@Data
public class FetchOrdersCommand {
    Status status;

}
