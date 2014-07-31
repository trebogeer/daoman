package com.trebogeer.daoman.jdbc;

import org.javatuples.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static com.trebogeer.daoman.jdbc.RSUtils.getByteBoolOrNull;
import static com.trebogeer.daoman.jdbc.RSUtils.getByteOrNull;
import static com.trebogeer.daoman.jdbc.RSUtils.getDate;
import static com.trebogeer.daoman.jdbc.RSUtils.getDateTimeOrNull;
import static com.trebogeer.daoman.jdbc.RSUtils.getDoubleOrNull;
import static com.trebogeer.daoman.jdbc.RSUtils.getIntegerOrNull;
import static com.trebogeer.daoman.jdbc.RSUtils.getLongOrNull;
import static com.trebogeer.daoman.jdbc.RSUtils.getShortOrNull;
import static com.trebogeer.daoman.jdbc.RSUtils.getStringOrNull;
import static com.trebogeer.daoman.jdbc.RowMappers.SimpleResultSet.*;
import static org.javatuples.Pair.with;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 3:38 PM
 */
public class RowMappers {

    public static RowMapper<Long> createLongRowMapper() {
        return createLongMapper();
    }

    public static RowMapper<Long> createLongMapper() {
        return new RowMapper<Long>() {
            @Override
            public Long map(int rowIndex, ResultSet rs) throws SQLException {
                return getLongOrNull(first, rs);
            }
        };
    }

    public static RowMapper<Long> createLongMapper(final int position) {
        return new RowMapper<Long>() {
            @Override
            public Long map(int rowIndex, ResultSet rs) throws SQLException {
                return getLongOrNull(position, rs);
            }
        };
    }

    public static RowMapper<Integer> createIntegerMapper() {
        return new RowMapper<Integer>() {
            @Override
            public Integer map(int rowIndex, ResultSet rs) throws SQLException {
                return getIntegerOrNull(first, rs);
            }
        };
    }

    public static RowMapper<String> createStringMapper() {
        return new RowMapper<String>() {
            @Override
            public String map(int rowIndex, ResultSet rs) throws SQLException {
                return getStringOrNull(first, rs);
            }
        };
    }

    public static RowMapper<String> createStringMapper(final int position) {
        return new RowMapper<String>() {
            @Override
            public String map(int rowIndex, ResultSet rs) throws SQLException {
                return getStringOrNull(position, rs);
            }
        };
    }

    public static RowMapper<Byte> createByteMapper() {
        return new RowMapper<Byte>() {
            @Override
            public Byte map(int rowIndex, ResultSet rs) throws SQLException {
                return getByteOrNull(first, rs);
            }
        };
    }

    public static RowMapper<Short> createShortMapper() {
        return new RowMapper<Short>() {
            @Override
            public Short map(int rowIndex, ResultSet rs) throws SQLException {
                return getShortOrNull(first, rs);
            }
        };
    }

    public static RowMapper<Double> createDoubleMapper() {
        return new RowMapper<Double>() {
            @Override
            public Double map(int rowIndex, ResultSet rs) throws SQLException {
                return getDoubleOrNull(first, rs);
            }
        };
    }

    public static RowMapper<Boolean> createBooleanMapper() {
        return new RowMapper<Boolean>() {
            @Override
            public Boolean map(int rowIndex, ResultSet rs) throws SQLException {
                return getByteBoolOrNull(first, rs);
            }
        };
    }

    public static RowMapper<Date> createDateMapper() {
        return new RowMapper<Date>() {
            @Override
            public Date map(int rowIndex, ResultSet rs) throws SQLException {
                return getDate(first, rs);
            }
        };
    }

    public static RowMapper<Date> createDateTimeMapper() {
        return new RowMapper<Date>() {
            @Override
            public Date map(int rowIndex, ResultSet rs) throws SQLException {
                return getDateTimeOrNull(first, rs);
            }
        };
    }

    public static RowMapper<Pair<Long, Integer>> createLongIntegerMapper() {
        return new RowMapper<Pair<Long, Integer>>() {
            @Override
            public Pair<Long, Integer> map(final int rowIndex, ResultSet rs) throws SQLException {
                return with(getLongOrNull(first, rs), getIntegerOrNull(second, rs));
            }
        };
    }


    public static RowMapper<Pair<Long, Long>> createLongLongMapper() {
        return pairLongs();
    }

    public static RowMapper<Pair<Long, Boolean>> createLongBoolMapper() {
        return new RowMapper<Pair<Long, Boolean>>() {
            @Override
            public Pair<Long, Boolean> map(int rowIndex, ResultSet rs) throws SQLException {
                return with(getLongOrNull(first, rs), getByteBoolOrNull(second, rs));
            }
        };
    }

    public static RowMapper<Pair<Long, Long>> pairLongs() {
        return new RowMapper<Pair<Long, Long>>() {
            @Override
            public Pair<Long, Long> map(int rowIndex, ResultSet rs) throws SQLException {
                return with(getLongOrNull(first, rs), getLongOrNull(second, rs));
            }
        };
    }

    public static RowMapper<Pair<Integer, Integer>> createIntegerIntegerMapper() {
        return pairInts();
    }

    public static RowMapper<Pair<Integer, Integer>> pairInts() {
        return new RowMapper<Pair<Integer, Integer>>() {
            @Override
            public Pair<Integer, Integer> map(int rowIndex, ResultSet rs) throws SQLException {
                return with(getIntegerOrNull(first, rs), getIntegerOrNull(second, rs));
            }
        };
    }

    public static RowMapper<Pair<Long, Integer>> pairLongInt() {
        return new RowMapper<Pair<Long, Integer>>() {
            @Override
            public Pair<Long, Integer> map(int rowIndex, ResultSet rs) throws SQLException {
                return with(getLongOrNull(first, rs), getIntegerOrNull(second, rs));
            }
        };
    }

    public static RowMapper<Pair<String, Long>> createStringLongMapper() {
        return pairStrLong();
    }

    public static RowMapper<Pair<String, Long>> pairStrLong() {
        return new RowMapper<Pair<String, Long>>() {
            @Override
            public Pair<String, Long> map(int rowIndex, ResultSet rs) throws SQLException {
                return with(getStringOrNull(first, rs), getLongOrNull(second, rs));
            }
        };
    }

    public static RowMapper<Pair<Long, String>> createLongStringMapper() {
        return pairLongStr();
    }

    public static RowMapper<Pair<Long, String>> pairLongStr() {
        return new RowMapper<Pair<Long, String>>() {
            @Override
            public Pair<Long, String> map(int rowIndex, ResultSet rs) throws SQLException {
                return with(getLongOrNull(first, rs), getStringOrNull(second, rs));
            }
        };
    }

    public static RowMapper<Pair<Integer, String>> createIntegerStringMapper() {
        return new RowMapper<Pair<Integer, String>>() {
            @Override
            public Pair<Integer, String> map(final int rowIndex, ResultSet rs) throws SQLException {
                return with(getIntegerOrNull(first, rs), getStringOrNull(second, rs));
            }
        };
    }

    public static RowMapper<Pair<String, String>> pairStrings() {
        return new RowMapper<Pair<String, String>>() {
            @Override
            public Pair<String, String> map(int rowIndex, ResultSet rs) throws SQLException {
                return with(getStringOrNull(first, rs), getStringOrNull(second, rs));
            }
        };
    }


    public enum SimpleResultSet {
        first, second
    }
}
