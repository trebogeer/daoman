package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * @author dimav
 *         Date: 8/7/13
 *         Time: 3:51 PM
 */
public abstract class Param<T> {

    T value;
    Type type;

    public enum Type {
        IN(1),
        OUT(2),
        INOUT(4);


        Type(int mask) {
            this.mask = mask;
        }

        int mask;

        public boolean isOut() {
            return (7 & mask) > 0;
        }

        public boolean isIn() {
            return (3 & mask) > 0;
        }
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

    public abstract void set(CallableStatement stmt, int pos) throws SQLException;

    public abstract void get(CallableStatement stmt, int pos) throws SQLException;

}
