package ru.kliuevia.springapp.config;

import java.util.UUID;

public interface Constants {

    interface Roles {
        UUID USER_ID = UUID.fromString( "9c8bef81-94e7-498c-8bef-8194e7798cda");

        String ADMIN_AUTHORITY = "ADMIN";
    }

    interface Sms {
        String TEXT = "Код активации аккаунта: %s";
    }
}
