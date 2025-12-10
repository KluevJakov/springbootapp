package ru.kliuevia.springapp.entity;

import lombok.Getter;

@Getter
public final class Tuple<K, V> {
    private final K t1;
    private final V t2;

    private Tuple(K t1, V t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public static <K, V> Tuple<K, V> of (K t1, V t2) {
        return new Tuple<>(t1, t2);
    }
}
