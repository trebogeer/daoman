package com.trebogeer.daoman.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 3:41 PM
 */
public final class RSUtils {
    private RSUtils() {
    }

//    public static Long getLong(int pos, ResultSet rs) throws SQLException {
//        final long rv = rs.getLong(pos);
//        return rs.wasNull() ? null : rv;
//    }
//
//    public static Long getLong(Enum pos, ResultSet rs) throws SQLException {
//        final long rv = rs.getLong(pos.ordinal() + 1);
//        return rs.wasNull() ? null : rv;
//    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static byte getByte(final Enum<?> e, ResultSet rs) throws SQLException {
        return getByte(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static byte getByte(final int pos, ResultSet rs) throws SQLException {
        return rs.getByte(pos);
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static boolean getByteBool(final Enum<?> e, ResultSet rs) throws SQLException {
        return getByteBool(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static boolean getByteBool(final int pos, ResultSet rs) throws SQLException {
        final byte b = getByte(pos, rs);
        return 1 == b;
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Boolean getByteBoolOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getByteBoolOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Boolean getByteBoolOrNull(final int pos, ResultSet rs) throws SQLException {
        final Byte b = getByteOrNull(pos, rs);
        if (b == null)
            return null;
        else
            return b == 1;
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Byte getByteOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getByteOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Byte getByteOrNull(final int pos, ResultSet rs) throws SQLException {
        final byte rv = rs.getByte(pos);
        return rs.wasNull() ? null : rv;
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static Date getDate(final Enum<?> e, ResultSet rs) throws SQLException {
        return getDate(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static Date getDate(final int pos, ResultSet rs) throws SQLException {
        final java.sql.Date rv = rs.getDate(pos);
        return rs.wasNull() || rv == null ? new Date(0) : new Date(rv.getTime());
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Date getDateOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getDateOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Date getDateOrNull(final int pos, ResultSet rs) throws SQLException {
        final java.sql.Date rv = rs.getDate(pos);
        return rs.wasNull() || rv == null ? null : new Date(rv.getTime());
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static Date getDateTime(final Enum<?> e, ResultSet rs) throws SQLException {
        return getDateTime(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static Date getDateTime(final int pos, ResultSet rs) throws SQLException {
        final java.sql.Timestamp rv = rs.getTimestamp(pos);
        return rs.wasNull() || rv == null ? new Date(0) : new Date(rv.getTime());
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Date getDateTimeOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getDateTimeOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Date getDateTimeOrNull(final int pos, ResultSet rs) throws SQLException {
        final java.sql.Timestamp rv = rs.getTimestamp(pos);
        return rs.wasNull() || rv == null ? null : new Date(rv.getTime());
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static double getDouble(final Enum<?> e, ResultSet rs) throws SQLException {
        return getDouble(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static double getDouble(final int pos, ResultSet rs) throws SQLException {
        return rs.getDouble(pos);
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Double getDoubleOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getDoubleOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Double getDoubleOrNull(final int pos, ResultSet rs) throws SQLException {
        final double rv = rs.getDouble(pos);
        return rs.wasNull() ? null : rv;
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static float getFloat(final Enum<?> e, ResultSet rs) throws SQLException {
        return getFloat(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static float getFloat(final int pos, ResultSet rs) throws SQLException {
        return rs.getFloat(pos);
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Float getFloatOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getFloatOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Float getFloatOrNull(final int pos, ResultSet rs) throws SQLException {
        final float rv = rs.getFloat(pos);
        return rs.wasNull() ? null : rv;
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static int getInteger(final Enum<?> e, ResultSet rs) throws SQLException {
        return getInteger(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static int getInteger(final int pos, ResultSet rs) throws SQLException {
        return rs.getInt(pos);
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Integer getIntegerOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getIntegerOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Integer getIntegerOrNull(final int pos, ResultSet rs) throws SQLException {
        final int rv = rs.getInt(pos);
        return rs.wasNull() ? null : rv;
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static long getLong(final Enum<?> e, ResultSet rs) throws SQLException {
        return getLong(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static long getLong(final int pos, ResultSet rs) throws SQLException {
        return rs.getLong(pos);
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Long getLongOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getLongOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Long getLongOrNull(final int pos, ResultSet rs) throws SQLException {
        final long rv = rs.getLong(pos);
        return rs.wasNull() ? null : rv;
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static short getShort(final Enum<?> e, ResultSet rs) throws SQLException {
        return getShort(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static short getShort(final int pos, ResultSet rs) throws SQLException {
        return rs.getShort(pos);
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Short getShortOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getShortOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Short getShortOrNull(final int pos, ResultSet rs) throws SQLException {
        final short rv = rs.getShort(pos);
        return rs.wasNull() ? null : rv;
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static String getString(final Enum<?> e, ResultSet rs) throws SQLException {
        return getString(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static String getString(final int pos, ResultSet rs) throws SQLException {
        final String rv = rs.getString(pos);
        return rs.wasNull() || rv == null ? "" : rv;
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static boolean getStringBool(final Enum<?> e, ResultSet rs) throws SQLException {
        return getStringBool(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static boolean getStringBool(final int pos, ResultSet rs) throws SQLException {
        final String s = getStringOrNull(pos, rs);
        return "Y".equalsIgnoreCase(s) || "1".equals(s);
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Boolean getStringBoolOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getStringBoolOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Boolean getStringBoolOrNull(final int pos, ResultSet rs) throws SQLException {
        final String s = getStringOrNull(pos, rs);
        if (s == null)
            return null;
        else
            return "Y".equalsIgnoreCase(s) || "1".equals(s);
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static String getStringOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getStringOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static String getStringOrNull(final int pos, ResultSet rs) throws SQLException {
        return rs.getString(pos);
    }

    /**
     * @param e 0-based enum value
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static Date getTime(final Enum<?> e, ResultSet rs) throws SQLException {
        return getTime(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return never returns null; when DB returns NULL, this method will return the type's default value
     * @throws SQLException
     */
    public static Date getTime(final int pos, ResultSet rs) throws SQLException {
        final java.sql.Time rv = rs.getTime(pos);
        return rs.wasNull() || rv == null ? new Date(0) : new Date(rv.getTime());
    }

    /**
     * @param e 0-based enum value
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Date getTimeOrNull(final Enum<?> e, ResultSet rs) throws SQLException {
        return getTimeOrNull(e.ordinal() + 1, rs);
    }

    /**
     * @param pos 1-based index into ResultSet columns
     * @return null reference when NULL value is returned from DB
     * @throws SQLException
     */
    public static Date getTimeOrNull(final int pos, ResultSet rs) throws SQLException {
        final java.sql.Time rv = rs.getTime(pos);
        return rs.wasNull() || rv == null ? null : new Date(rv.getTime());
    }

}
