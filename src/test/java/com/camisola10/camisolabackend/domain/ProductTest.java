package com.camisola10.camisolabackend.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    public void shouldHaveUniqueId() {
        var p1 = Product.create("p1", null, null, false, null);
        var p2 = Product.create("p2", null, null, false, null);

        assertThat(p1.getId()).isNotEqualTo(p2.getId());
    }

}
