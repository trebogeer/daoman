package com.trebogeer.daoman.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

/**
 * @author dimav
 *         Date: 8/1/14
 *         Time: 10:50 AM
 */
public class PUtils {

    public static void setBoolToByte(int pos, boolean v, PreparedStatement stm) throws SQLException {
        setByte(pos, v ? (byte) 1 : (byte) 0, stm);
    }

    public static void setBoolToByteOrNull(int pos, Boolean v, PreparedStatement stm) throws SQLException {
        if (v == null)
            setByteOrNull(pos, null, stm);
        else
            setBoolToByte(pos, v, stm);
    }

    public static void setBoolToString(int pos, boolean v, PreparedStatement stm) throws SQLException {
        setString(pos, v ? "Y" : "N", stm);
    }

    public static void setBoolToStringOrNull(int pos, Boolean v, PreparedStatement stm) throws SQLException {
        if (v == null)
            setStringOrNull(pos, null, stm);
        else
            setBoolToString(pos, v, stm);
    }

    public static void setByte(int pos, byte v, PreparedStatement stm) throws SQLException {
        stm.setByte(pos, v);
    }

    public static void setByteOrNull(int pos, Byte v, PreparedStatement stm) throws SQLException {
        if (v == null)
            stm.setNull(pos, Types.TINYINT);
        else
            stm.setByte(pos, v);
    }

    public static void setDate(int pos, Date val, PreparedStatement stm) throws SQLException {
        setDateOrNull(pos, val == null ? new Date(0) : val, stm);
    }

    public static void setDateOrNull(int pos, Date val, PreparedStatement stm) throws SQLException {
        if (val == null)
            stm.setNull(pos, Types.DATE);
        else
            stm.setDate(pos, new java.sql.Date(val.getTime()));
    }

    public static void setDateTime(int pos, Date val, PreparedStatement stm) throws SQLException {
        setDateTimeOrNull(pos, val == null ? new Date(0) : val, stm);
    }

    public static void setDateTimeOrNull(int pos, Date val, PreparedStatement stm) throws SQLException {
        if (val == null)
            stm.setNull(pos, Types.TIMESTAMP);
        else
            stm.setTimestamp(pos, new Timestamp(val.getTime()));
    }

    public static void setDouble(int pos, double v, PreparedStatement stm) throws SQLException {
        stm.setDouble(pos, v);
    }

    public static void setDoubleOrNull(int pos, Double v, PreparedStatement stm) throws SQLException {
        if (v == null)
            stm.setNull(pos, Types.DOUBLE);
        else
            stm.setDouble(pos, v);
    }

    public static void setFloat(int pos, float v, PreparedStatement stm) throws SQLException {
        stm.setFloat(pos, v);
    }

    public static void setFloatOrNull(int pos, Float v, PreparedStatement stm) throws SQLException {
        if (v == null)
            stm.setNull(pos, Types.FLOAT);
        else
            stm.setFloat(pos, v);
    }

    public static void setInteger(int pos, int v, PreparedStatement stm) throws SQLException {
        stm.setInt(pos, v);
    }

    public static void setIntegerOrNull(int pos, Integer val, PreparedStatement stm) throws SQLException {
        if (val == null)
            stm.setNull(pos, Types.INTEGER);
        else
            stm.setInt(pos, val);
    }

    public static void setLong(int pos, long v, PreparedStatement stm) throws SQLException {
        stm.setLong(pos, v);
    }

    public static void setLongOrNull(int pos, Long val, PreparedStatement stm) throws SQLException {
        if (val == null)
            stm.setNull(pos, Types.BIGINT);
        else
            stm.setLong(pos, val);
    }

    public static void setShort(int pos, short v, PreparedStatement stm) throws SQLException {
        stm.setShort(pos, v);
    }

    public static void setShortOrNull(int pos, Short v, PreparedStatement stm) throws SQLException {
        if (v == null)
            stm.setNull(pos, Types.SMALLINT);
        else
            stm.setShort(pos, v);
    }

    public static void setString(int pos, String val, PreparedStatement stm) throws SQLException {
        setStringOrNull(pos, val == null ? "" : val, stm);
    }

    public static void setStringOrNull(int pos, String val, PreparedStatement stm) throws SQLException {
        if (val == null)
            stm.setNull(pos, Types.VARCHAR);
        else
            stm.setString(pos, val);
    }

    public static void setTime(final int pos, final Date val, PreparedStatement stm) throws SQLException {
        setTimeOrNull(pos, val == null ? new Date(0) : val, stm);
    }

    public static void setTimeOrNull(final int pos, final Date val, PreparedStatement stm) throws SQLException {
        if (val == null)
            stm.setNull(pos, Types.TIME);
        else
            stm.setTime(pos, new java.sql.Time(val.getTime()));
    }

    // getters
    public static byte getByte(int pos, CallableStatement cstmt) throws SQLException {
        return cstmt.getByte(pos);
    }

    public static Byte getByteOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final byte rv = cstmt.getByte(pos);
        return cstmt.wasNull() ? null : rv;
    }

    public static boolean getByteToBool(final int pos, CallableStatement cstmt) throws SQLException {
        final byte b = getByte(pos, cstmt);
        return 1 == b;
    }

    public static Boolean getByteToBoolOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final Byte b = getByteOrNull(pos, cstmt);
        return b == null ? null : 1 == b;
    }

    public static Date getDate(final int pos, CallableStatement cstmt) throws SQLException {
        final java.sql.Date rv = cstmt.getDate(pos);
        return cstmt.wasNull() || rv == null ? new Date(0) : new Date(rv.getTime());
    }

    public static Date getDateOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final java.sql.Date rv = cstmt.getDate(pos);
        return cstmt.wasNull() || rv == null ? null : new Date(rv.getTime());
    }

    public static Date getDateTime(final int pos, CallableStatement cstmt) throws SQLException {
        final java.sql.Timestamp rv = cstmt.getTimestamp(pos);
        return cstmt.wasNull() || rv == null ? new Date(0) : new Date(rv.getTime());
    }

    public static Date getDateTimeOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final java.sql.Timestamp rv = cstmt.getTimestamp(pos);
        return cstmt.wasNull() || rv == null ? null : new Date(rv.getTime());
    }

    public static double getDouble(final int pos, CallableStatement cstmt) throws SQLException {
        return cstmt.getDouble(pos);
    }

    public static Double getDoubleOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final double rv = cstmt.getDouble(pos);
        return cstmt.wasNull() ? null : rv;
    }

    public static double getFloat(final int pos, CallableStatement cstmt) throws SQLException {
        return cstmt.getFloat(pos);
    }

    public static Float getFloatOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final float rv = cstmt.getFloat(pos);
        return cstmt.wasNull() ? null : rv;
    }

    public static int getInteger(final int pos, CallableStatement cstmt) throws SQLException {
        return cstmt.getInt(pos);
    }

    public static Integer getIntegerOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final int rv = cstmt.getInt(pos);
        return cstmt.wasNull() ? null : rv;
    }

    public static long getLong(final int pos, CallableStatement cstmt) throws SQLException {
        return cstmt.getLong(pos);
    }

    public static Long getLongOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final long rv = cstmt.getLong(pos);
        return cstmt.wasNull() ? null : rv;
    }

    public static short getShort(final int pos, CallableStatement cstmt) throws SQLException {
        return cstmt.getShort(pos);
    }

    public static Short getShortOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final short rv = cstmt.getShort(pos);
        return cstmt.wasNull() ? null : rv;
    }

    /**
     * get value at position pos. Null gets converted to empty string - "".
     *
     * @param pos
     * @return
     * @throws SQLException
     */
    public static String getString(final int pos, CallableStatement cstmt) throws SQLException {
        final String rv = cstmt.getString(pos);
        return cstmt.wasNull() ? "" : rv;
    }

    public static String getStringOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        return cstmt.getString(pos);
    }

    public static boolean getStringToBool(final int pos, CallableStatement cstmt) throws SQLException {
        final String s = getStringOrNull(pos, cstmt);
        return "Y".equals(s);
    }

    public static Boolean getStringToBoolOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final String s = getStringOrNull(pos, cstmt);
        return s == null ? null : "Y".equalsIgnoreCase(s);
    }

    public static Date getTime(final int pos, CallableStatement cstmt) throws SQLException {
        final java.sql.Time rv = cstmt.getTime(pos);
        return cstmt.wasNull() || rv == null ? new Date(0) : new Date(rv.getTime());
    }

    public static Date getTimeOrNull(final int pos, CallableStatement cstmt) throws SQLException {
        final java.sql.Time rv = cstmt.getTime(pos);
        return cstmt.wasNull() || rv == null ? null : new Date(rv.getTime());
    }

}
