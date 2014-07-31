package com.trebogeer.daoman.jdbc;

import com.trebogeer.daoman.param.PByte;
import com.trebogeer.daoman.param.PDate;
import com.trebogeer.daoman.param.PDateTime;
import com.trebogeer.daoman.param.PDouble;
import com.trebogeer.daoman.param.PErrOut;
import com.trebogeer.daoman.param.PFloat;
import com.trebogeer.daoman.param.PInt;
import com.trebogeer.daoman.param.PLong;
import com.trebogeer.daoman.param.PShort;
import com.trebogeer.daoman.param.PStr;
import com.trebogeer.daoman.param.PTime;

import java.sql.Timestamp;
import java.util.Date;

import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 2:45 PM
 */
public final class SPParams {
    private SPParams() {
    }

    public static PErrOut outStatus() {
        return new PErrOut(null);
    }

//    public static BoolByteParam inBoolByte(Boolean b) {
//        return new BoolByteParam(b);
//    }
//
//    public static BoolByteParam outBoolByte() {
//        return new BoolByteParam();
//    }
//
//    public static BoolByteParam inOutBoolByte(Boolean b) {
//        return new BoolByteParam(b, INOUT);
//    }
//
//    public static BoolStringParam inBoolStr(Boolean b) {
//        return new BoolStringParam(b);
//    }
//
//    public static BoolStringParam inOutBoolStr(Boolean b) {
//        return new BoolStringParam(b, INOUT);
//    }
//
//    public static BoolStringParam outBoolStr() {
//        return new BoolStringParam();
//    }

    public static PByte inByte(Byte b) {
        return new PByte(b, IN);
    }

    public static PByte inOutByte(Byte b) {
        return new PByte(b, INOUT);
    }

    public static PByte outByte() {
        return new PByte(null, OUT);
    }

    public static PDate inDate(Date d) {
        return new PDate(d, IN);
    }

    public static PDate inDate(long d) {
        return new PDate(new Date(d), IN);
    }

    public static PDate inOutDate(Date d) {
        return new PDate(d, INOUT);
    }

    public static PDate outDate() {
        return new PDate(null, OUT);
    }

    public static PDateTime inDateTime(Date date) {
        return new PDateTime(date, IN);
    }

    public static PDateTime inDateTime(long d) {
        return new PDateTime(new Date(d), IN);
    }

    public static PDateTime inOutDateTime(Date d) {
        return new PDateTime(d, INOUT);
    }

    public static PDateTime outDateTime() {
        return new PDateTime(null, OUT);
    }

    public static PDouble inDouble(Double dbl) {
        return new PDouble(dbl, IN);
    }

    public static PDouble inOutDouble(Double dbl) {
        return new PDouble(dbl, INOUT);
    }

    public static PDouble outDouble() {
        return new PDouble(null, OUT);
    }

    public static PFloat inFloat(Float fl) {
        return new PFloat(fl, IN);
    }

    public static PFloat inOutFloat(Float fl) {
        return new PFloat(fl, INOUT);
    }

    public static PFloat outFloat() {
        return new PFloat(null, OUT);
    }

    public static PInt inInt(Integer i) {
        return new PInt(i, IN);
    }

    public static PInt inOutInt(Integer i) {
        return new PInt(i, INOUT);
    }

    public static PInt outInt() {
        return new PInt(null, OUT);
    }

    public static PLong inLong(Long l) {
        return new PLong(l, IN);
    }

    public static PLong inOutLong(Long l) {
        return new PLong(l, INOUT);
    }

    public static PLong outLong() {
        return new PLong(null, OUT);
    }

    public static PShort inShort(Short s) {
        return new PShort(s, IN);
    }

    public static PShort inOutShort(Short s) {
        return new PShort(s, INOUT);
    }

    public static PShort outShort() {
        return new PShort(null, OUT);
    }

    public static PStr inStr(String str) {
        return new PStr(str, IN);
    }

    public static PStr inOutStr(String str) {
        return new PStr(str, INOUT);
    }

    public static PStr outStr() {
        return new PStr(null, OUT);
    }

//    public static StringParamList inList(Collection<?> collection) {
//        return new StringParamList(collection);
//    }
//
//    public static StringParamList inList(Collection<?> collection, String separator) {
//        return new StringParamList(collection, separator);
//    }

//    public static StringParamList inList(Object[] collection) {
//        return new StringParamList(collection);
//    }

    public static PTime inTime(long time) {
        return new PTime(new Timestamp(time), IN);
    }

    public static PTime inTime(Date time) {
        return new PTime(new Timestamp(time.getTime()), IN);
    }

    public static PTime inOutTime(Date time) {
        return new PTime(new Timestamp(time.getTime()), INOUT);
    }

    public static PTime outTime() {
        return new PTime(null, OUT);
    }

}
