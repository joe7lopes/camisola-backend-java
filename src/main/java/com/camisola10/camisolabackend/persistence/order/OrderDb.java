package com.camisola10.camisolabackend.persistence.order;

import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document("order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
class OrderDb {
    @Id
    private String id;
    @Indexed(name = "orderId_index",unique = true)
    private String orderId;
    private ShippingAddress shippingAddress;
    private List<OrderItem> items;
    private Order.Status status;
    private BigDecimal total;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModified;
}
