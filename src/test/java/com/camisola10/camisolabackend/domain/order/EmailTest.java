package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.Email.InvalidEmailException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    public void shouldThrowForNull() {
        String email = null;

        InvalidEmailException ex = assertThrowsException(email);

        assertMessage(email, ex);
    }

    @Test
    public void shouldThrowForEmpty() {
        String email = "";

        InvalidEmailException ex = assertThrowsException(email);

        assertMessage(email, ex);
    }

    @Test
    public void shouldThrowForEmailWithoutDomain() {
        String email = "asds@.com";

        InvalidEmailException ex = assertThrowsException(email);

        assertMessage(email, ex);
    }

    private InvalidEmailException assertThrowsException(String email) {
        return assertThrows(InvalidEmailException.class, () -> new Email(email));
    }

    private void assertMessage(String email, Exception ex) {
        assertThat(ex.getMessage()).isEqualTo("Invalid email address " + email);
    }
}
