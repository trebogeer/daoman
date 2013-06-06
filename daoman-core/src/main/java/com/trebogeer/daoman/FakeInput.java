package com.trebogeer.daoman;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author dimav
 *         Date: 1/17/12
 *         Time: 10:27 AM
 */
public final class FakeInput {

    private FakeInput() {
    }

    public static Byte negByte() {
        return (byte)1;
    }

    public static Short negShort() {
        return (short)1;
    }

    public static Integer negInteger() {
        return 1;
    }

    public static Long negLong() {
        return 1L;
    }

    public static Date date() {
        return new Date();
    }

    public static Time time() {
        return new Time(new Date().getTime());
    }

    public static Timestamp timestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static Double negDouble() {
        return 0.0;
    }

    public static Float negFloat() {
        return (float)0.0;
    }

    public static String str() {
        return "1";
    }

}
