package com.camisola10.camisolabackend.notification;

import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.events.OrderCreatedEvent;
import com.camisola10.camisolabackend.domain.order.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class EmailConverter implements Converter<OrderCreatedEvent, Email> {

    private Order order;

    @Override
    public Email convert(OrderCreatedEvent event) {
        this.order = event.getOrder();
        return Email.builder()
                .from("camisola10")
                .to(event.getOrder().getShippingAddress().getEmailAddress().asString())
                .subject("Camisola10 Encomenda " + event.getOrder().getId().asString())
                .message(formatContent())
                .build();
    }

    private String formatContent() {
        var firstName = order.getShippingAddress().getFirstName();
        var lastName = order.getShippingAddress().getLastName();
        var orderId = order.getId().asString();

        return "<html>" +
                "<head></head>" +
                "<body>" +
                "<p>Olá " + firstName + " " + lastName + " ,</p>" +
                "<p>A sua encomenda <b>" + orderId + "</b> foi recebida.</p>" +
                "<div><strong>Detalhes da encomenda:</strong></div>" +
                formatItems() +
                "<div style=\"margin-top: 1rem;\">Total a pagar: " + order.getTotal().asString() + "€</div>" +
                formatAddress() +
                formatDeliveryTime() +
                formatDisclaimer() +
                "<p>Obrigado,</p>" +
                "<p>Camisola10</p>" +
                "</body>" +
                "</html>";
    }

    private String formatItems() {
        var items = order.getItems();
        var result = new StringBuilder();
        for (com.camisola10.camisolabackend.domain.order.OrderItem item : items) {
            result.append("<div style=\"margin-top: 1rem; margin-left: 1rem;\">").append(item.getProduct().getName()).append("</div>")
                    .append("<div style=\"margin-left: 1rem;\">" + "Tamanho: ").append(item.getSize().getSize().asString()).append("</div>")
                    .append("<div style=\"margin-left: 1rem;\">" + "Nome: ").append(item.getStampingName()).append("</div>")
                    .append("<div style=\"margin-left: 1rem;\">" + "N&uacute;mero: ").append(item.getStampingNumber()).append("</div>");
        }

        return result.toString();
    }

    private String formatAddress() {
        String address = order.getShippingAddress().getAddress();
        String postCode = order.getShippingAddress().getPostCode();
        String city = order.getShippingAddress().getCity();
        return String.format("<div style=\"margin-top: 2rem\"><b>Morada de envio:</b> %s, %s %s.</div>", address, postCode, city);
    }

    private String formatDeliveryTime() {
        return "<p style=\"margin-top: 1rem\"><b>Prazos de entrega:</b></p>" +
                "<p>Continente: 2 a 3 dias úteis.</p>" +
                "<p>Ilhas: 4 a 5 dias úteis.</p>" +
                "<p>Com personalização: +1 um dia útil.</p>";
    }

    private String formatDisclaimer() {
        return "<p style=\"margin-top: 2rem\">Este email &eacute; automatico. Para qualquer d&uacute;vida entre em contacto comigo atrav&eacute;s do WhatsApp <strong>919 255 684</strong></p>" +
                "<p>O pagamento &eacute; feito &agrave; cobran&ccedil;a.</p>";
    }

}
