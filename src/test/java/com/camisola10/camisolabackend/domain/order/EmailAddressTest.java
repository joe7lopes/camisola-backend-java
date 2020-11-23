package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.EmailAddress;
import com.camisola10.camisolabackend.domain.EmailAddress.InvalidEmailAddressException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailAddressTest {

    @Test
    public void shouldThrowForNull() {
        String email = null;

        InvalidEmailAddressException ex = assertThrowsException(email);

        assertMessage(email, ex);
    }

    @Test
    public void shouldThrowForEmpty() {
        String email = "";

        InvalidEmailAddressException ex = assertThrowsException(email);

        assertMessage(email, ex);
    }

    @Test
    public void shouldThrowForEmailWithoutDomain() {
        String email = "asds@.com";

        InvalidEmailAddressException ex = assertThrowsException(email);

        assertMessage(email, ex);
    }

    private InvalidEmailAddressException assertThrowsException(String email) {
        return assertThrows(InvalidEmailAddressException.class, () -> new EmailAddress(email));
    }

    private void assertMessage(String email, Exception ex) {
        assertThat(ex.getMessage()).isEqualTo("Invalid email address " + email);
    }
}
