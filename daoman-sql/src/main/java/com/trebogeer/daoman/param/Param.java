package com.trebogeer.daoman.param;

/**
 * @author dimav
 *         Date: 8/7/13
 *         Time: 3:51 PM
 */
public abstract class Param<T> {

    T value;
    Type type;

    public enum Type {
        IN,
        OUT,
        INOUT
    }

    public Param(final T value, Type type) {
        this.value = value;
        this.type = type;
    }

    public T value() {
        return value;
    }

    public Type type() {
        return type;
    }


}
