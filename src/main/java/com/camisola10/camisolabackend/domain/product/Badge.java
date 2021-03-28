package com.camisola10.camisolabackend.domain.product;

import lombok.Value;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
public class Badge {
    String id;
    String name;

    public Badge(String id, String name) {
        this.id = id;
        this.name = name;
        validate();
    }

    private void validate() {
        if (isBlank(id) || isBlank(name)) {
            throw new InvalidBadgeException(this);
        }
    }

    public static class InvalidBadgeException extends RuntimeException {
        public InvalidBadgeException(Badge badge) {
            super("Invalid badge " + badge);
        }
    }
}
