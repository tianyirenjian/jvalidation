package com.tianyisoft.jvalidate.utils;

public class Tuple2 <T, K> {
    private T v0;
    private K v1;

    public Tuple2(T v0, K v1) {
        this.v0 = v0;
        this.v1 = v1;
    }

    @SuppressWarnings("unchecked")
    public static <T, K> Tuple2<T, K> castFrom(Object obj) {
        if (obj instanceof Tuple2) {
            return (Tuple2<T, K>) obj;
        }
        return null;
    }

    public T getV0() {
        return v0;
    }

    public void setV0(T v0) {
        this.v0 = v0;
    }

    public K getV1() {
        return v1;
    }

    public void setV1(K v1) {
        this.v1 = v1;
    }

    @Override
    public String toString() {
        return "Tuple2{" +
                "v0=" + v0 +
                ", v1=" + v1 +
                '}';
    }
}
