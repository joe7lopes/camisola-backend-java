package com.camisola10.camisolabackend.application.port.in.command.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Base64ImageTest {


    @Test
    public void shouldEscapeCharacters() {
        Base64Image image = new Base64Image("FCP PN2 criança.JPG", null, null);
        assertThat(image.getNameAsUTF8()).isEqualTo("FCP PN2 crianca.JPG");
    }
}