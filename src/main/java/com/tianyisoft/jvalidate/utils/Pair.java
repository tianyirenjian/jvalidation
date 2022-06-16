package com.tianyisoft.jvalidate.utils;

public class Pair<T, K> extends Tuple2<T, K> {
    public Pair(T v0, K v1) {
        super(v0, v1);
    }

    public static <T, K> Pair<T, K> of(T v0, K v1) {
        return new Pair<>(v0, v1);
    }

    public T first() {
        return getV0();
    }

    public K second() {
        return getV1();
    }
}
