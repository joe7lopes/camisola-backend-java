package com.camisola10.camisolabackend.application.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
class RandomIdGenerator {

    private static final char[] _base62chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final Random _random = new Random();

    public String getBase62(int length) {
        var sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(_base62chars[_random.nextInt(62)]);
        }

        return sb.toString();
    }

    public String getBase36(int length) {
        var sb = new StringBuilder(length);

        for (int i = 0; i < length; i++)
            sb.append(_base62chars[_random.nextInt(36)]);

        return sb.toString();
    }

    public String base36WithDate() {
        var randomId = getBase36(4);
        LocalDate date = LocalDate.now();
        return String.format("%s-%s", date, randomId);
    }
}

