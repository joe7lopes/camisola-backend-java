package com.camisola10.camisolabackend.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Email {
    String from;
    String to;
    String subject;
    String message;
}
