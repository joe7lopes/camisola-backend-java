package com.camisola10.camisolabackend.domain.product;

import com.camisola10.camisolabackend.domain.Money;
import lombok.Value;

import java.util.UUID;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
public class ProductSize {
    ProductSizeId id;
    Size size;
    Money price;

    @Value
    public static class ProductSizeId {
        UUID value;

        public ProductSizeId(UUID value) {
            this.value = value;
            validate();
        }


        public static ProductSizeId create() {
            return new ProductSizeId(UUID.randomUUID());
        }

        private void validate() {
            if (isNull(value) || isBlank(value.toString())) {
                throw new InvalidProductSizeId();
            }
        }

        static class InvalidProductSizeId extends RuntimeException {
        }
    }
}
