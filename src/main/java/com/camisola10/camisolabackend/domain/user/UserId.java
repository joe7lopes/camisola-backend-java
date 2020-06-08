package com.camisola10.camisolabackend.domain.user;

import lombok.Value;

import java.util.UUID;

import static java.util.Objects.isNull;

@Value
public class UserId {
    UUID value;

    private UserId(UUID value) {
        this.value = value;
        validate();
    }

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId from(UUID userId) {
        return new UserId(userId);
    }

    public static UserId from(String userId) {
        return new UserId(UUID.fromString(userId));
    }

    public String asString(){
        return value.toString();
    }

    private void validate() {
        if (isNull(value)) {
            throw new InvalidUserIdException(this);
        }
    }

    static class InvalidUserIdException extends RuntimeException {
        public InvalidUserIdException(UserId userId) {
            super("Invalid User Id:" + userId);
        }
    }
}
