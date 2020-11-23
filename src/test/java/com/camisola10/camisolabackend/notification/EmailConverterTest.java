package com.camisola10.camisolabackend.notification;

import com.camisola10.camisolabackend.domain.EmailAddress;
import com.camisola10.camisolabackend.domain.events.OrderCreatedEvent;
import com.camisola10.camisolabackend.domain.order.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EmailConverterTest {
    
    @Test
    public void shouldConvertEventToEmail() {
      
        EmailConverter converter = new EmailConverter();
        var order = Mockito.mock(Order.class, Mockito.RETURNS_DEEP_STUBS);
        var emailAddress = "mockAddress@kk.com";
        var to = new EmailAddress(emailAddress);
        when(order.getShippingAddress().getEmailAddress()).thenReturn(to);
        when(order.getId()).thenReturn(Order.OrderId.create("1234"));

        //WHEN
        var email = converter.convert(new OrderCreatedEvent(order));

        //THEN
        assertThat(email.getFrom()).isEqualTo("camisola10");
        assertThat(email.getTo()).isEqualTo(emailAddress);
        assertThat(email.getSubject()).isEqualTo("Camisola10 Encomenda 1234");
        assertThat(email.getMessage()).isEqualTo("<html><head></head><body><p>Olá null null ,</p><p>A sua encomenda <b>1234</b> foi recebida.</p><div><strong>Detalhes da encomenda:</strong></div><div style=\"margin-top: 1rem;\">Total a pagar: null€</div><div style=\"margin-top: 2rem\"><b>Morada de envio:</b> null, null null.</div><p style=\"margin-top: 1rem\"><b>Prazos de entrega:</b></p><p>Continente: 2 a 3 dias úteis.</p><p>Ilhas: 4 a 5 dias úteis.</p><p>Com personalização: +1 um dia útil.</p><p style=\"margin-top: 2rem\">Este email &eacute; automatico. Para qualquer d&uacute;vida entre em contacto comigo atrav&eacute;s do WhatsApp <strong>919 255 684</strong></p><p>O pagamento &eacute; feito &agrave; cobran&ccedil;a.</p><p style=\"margin-top: 2rem;\">Obrigado,</p><p>Camisola10</p></body></html>");

    }

}