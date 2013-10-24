package com.trebogeer.daoman.util;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * @author dimav
 * Date: 6/5/13
 * Time: 4:30 PM
 */
public class JDBCUtil {

    public static void close(final Connection connection) {
        close(null, null, connection);
    }

    public static void close(final ResultSet rs) {
        close(rs, null, null);
    }

    public static void close(final ResultSet rs, final CallableStatement stmt) {
        close(rs, stmt, null);
    }

    public static void close(final ResultSet resultset, final Statement statement, final Connection connection) {
        try {
            if (resultset != null)
                resultset.close();
        } catch (final Throwable sqlexception) {
            // nothing to do
        }
        try {
            if (statement != null)
                statement.close();
        } catch (final Throwable sqlexception1) {
            // nothing to do
        }
        try {
            if (connection != null)
                connection.close();
        } catch (final Throwable sqlexception2) {
            // nothing to do
        }
    }

    public static void close(final Statement stmt) {
        close(null, stmt, null);
    }

    public static void close(final Statement statement, final Connection connection) {
        close(null, statement, connection);
    }

    public static void commit(final Connection connection) throws SQLException {
        try {
            connection.commit();
        } catch (final SQLException sqle) {
            throw sqle;
        }
    }

    public static String generateSqlCallString(final String procName, final int paramCount) {
        final StringBuilder sb = new StringBuilder();
        sb.append("{ call ").append(procName).append("(");
        if (paramCount > 0) {
            for (int i = 0; i < paramCount - 1; i++) {
                sb.append("?,");
            }
            sb.append("?");
        }
        sb.append(") }");
        return sb.toString();
    }

    public static Connection getConnection(final DataSource ds, final boolean autoCommit) throws SQLException {
        return autoCommit ? getConnectionWithAutoCommit(ds) : getConnectionWithManualCommit(ds);
    }

    public static Connection getConnectionWithAutoCommit(final DataSource ds) throws SQLException {
        final Connection connection = ds.getConnection();
        if (!connection.getAutoCommit())
            connection.setAutoCommit(true);
        return connection;
    }

    public static Connection getConnectionWithManualCommit(final DataSource ds) throws SQLException {
        final Connection connection = ds.getConnection();
        if (connection.getAutoCommit())
            connection.setAutoCommit(false);
        return connection;
    }

    public static void rollback(final Connection connection) {
        try {
            if (connection != null)
                connection.rollback();
        } catch (final SQLException sqle) {

        }
    }

    public static String sqlTypeAsString(final int sqlType) {
        for (final Field f : Types.class.getDeclaredFields()) {
            try {
                if (f.getInt(null) == sqlType)
                    return f.getName();
            } catch (final Exception e) {
            }
        }
        return null;
    }
}
