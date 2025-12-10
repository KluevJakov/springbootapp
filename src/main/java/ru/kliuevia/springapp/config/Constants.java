package ru.kliuevia.springapp.config;

import java.util.UUID;

public interface Constants {

    interface Roles {
        UUID USER_ID = UUID.fromString( "9c8bef81-94e7-498c-8bef-8194e7798cda");
        UUID ADMIN_ID = UUID.fromString( "ad6917e1-19d1-418d-a917-e119d1018d3b");

        String ADMIN_AUTHORITY = "ADMIN";
        String USER_AUTHORITY = "USER";
    }

    interface Sms {
        String TEXT = "Код активации аккаунта: %s";
    }
}
