package ru.kliuevia.springapp.utils;

import java.util.Date;

public class DateUtils {

    public static Date now() {
        return new Date();
    }

    public static Date plus(Long startDate, Long endDate) {
        return new Date(startDate + endDate);
    }
}
