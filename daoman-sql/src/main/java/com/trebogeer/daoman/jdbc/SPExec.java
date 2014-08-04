package com.trebogeer.daoman.jdbc;

import com.google.common.collect.Multimap;
import com.trebogeer.daoman.param.Param;
import com.trebogeer.daoman.util.JDBCUtil;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.trebogeer.daoman.util.JDBCUtil.close;
import static java.util.Collections.singleton;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:24 PM
 */
public class SPExec {

    private static final Logger logger = LoggerFactory.getLogger(SPExec.class);

    private SPExec() {
    }

    public static <K, V> Map<K, V> queryMap(final DataSource ds, final String procName, final RowMapper<Pair<K, V>> mapper, final Param... params) {
        return queryMap(singleton(ds), procName, mapper, params);
    }

    public static <K, V> Map<K, V> queryMap(final Collection<DataSource> ds, final String procName, final RowMapper<Pair<K, V>> mapper, final Param... params) {
        checkNotNull(mapper, "Row Mapper can't be null");
        MapRSReader<K, V> reader = new MapRSReader<K, V>(mapper);
        call(ds, procName, reader, params);
        return reader.getValue();
    }

    public static <V> List<V> queryList(final DataSource ds, final String procName, final RowMapper<V> mapper, final Param... params) {
        return queryList(singleton(ds), procName, mapper, params);
    }

    public static <V> List<V> queryList(final Collection<DataSource> ds, final String procName, final RowMapper<V> mapper, final Param... params) {
        checkNotNull(mapper, "Row Mapper can't be null");
        ListRSReader<V> reader = new ListRSReader<V>(mapper);
        call(ds, procName, reader, params);
        return reader.getValue();
    }

    public static <K, V> Multimap<K, V> queryMultimap(final DataSource ds, final String procName, final RowMapper<Pair<K, V>> mapper, final Param... params) {
        return queryMultimap(singleton(ds), procName, mapper, params);
    }

    public static <K, V> Multimap<K, V> queryMultimap(final Collection<DataSource> ds, final String procName, final RowMapper<Pair<K, V>> mapper, final Param... params) {
        checkNotNull(mapper, "Row Mapper can't be null");
        MultimapRSReader<K, V> reader = new MultimapRSReader<K, V>(mapper);
        call(ds, procName, reader, params);
        return reader.getValue();
    }

    public static <T> void queryWithCallback(DataSource ds, final String procName, final RowMapper<T> mapper, final Callback<T> callback, final Param... params) {
        queryWithCallback(singleton(ds), procName, mapper, callback, params);
    }

    public static <T> void queryWithCallback(final Collection<DataSource> ds, final String procName, final RowMapper<T> mapper, final Callback<T> callback, final Param... params) {
        checkNotNull(ds, "Data source can't be null");
        checkNotNull(callback, "Callback can't be null");
        CallbackRSReader<T> reader = new CallbackRSReader<T>(mapper, callback);
        call(ds, procName, reader, params);
    }

    public static void call(final Collection<DataSource> dsc, final String procName, final RSReader reader, final Param... params) {
        Iterator<DataSource> iterator = dsc.iterator();
        DataSource ds = null;
        try {
            while (iterator.hasNext()) {
                ds = iterator.next();
                execute(ds, procName, reader, params);
            }
        } catch (SQLException e) {
            if (logger != null && ds != null)
                logger.error("Error executing stored procedure {} with params {} on data source[s] [{}]", procName, newArrayList(params), ds, e);
            throw new VRuntime("Database error occurred.", e);
        }
    }


    public static void call(final DataSource ds, final String procName, final Param... params) {
        call(singleton(ds), procName, params);
    }

    public static void call(final Collection<DataSource> ds, final String procName, final Param... params) {
        checkNotNull(procName, "Procedure name can't be null");
        checkNotNull(ds, "Data source can't be null");
        call(ds, procName, null, params);
    }

    public static void call(DataSource ds, final String procName, final RSReader reader, final Param... params) {
        call(singleton(ds), procName, reader, params);
    }

    public static void execute(final DataSource ds, final String procName, final Param... params) throws SQLException {
        execute(singleton(ds), procName, params);
    }

    public static void execute(final Collection<DataSource> ds, final String procName, final Param... params) throws SQLException {
        checkNotNull(ds, "DataSource can't be null");
        for (DataSource dataSource : ds) {
            execute(dataSource, procName, null, params);
        }
    }

    public static <T> T querySingle(final DataSource ds, final String procName, final RowMapper<T> mapper, final Param... params) {
        PojoRSReader<T> reader = new PojoRSReader<T>(mapper);
        call(ds, procName, reader, params);
        return reader.getValue();
    }

    private static void execute(final Wrapper wrapper, final String procName, final RSReader reader, final Param... params) throws SQLException {
        checkNotNull(procName, "Procedure name can't be null");
        checkNotNull(wrapper, "Data source or Connection can't be null");
        if (wrapper instanceof DataSource) {
            DataSource ds = (DataSource) wrapper;
            jdbcCall(ds.getConnection(), procName, reader, params);
        } else if (wrapper instanceof Connection) {
            Connection conn = (Connection) wrapper;
            jdbcCall(conn, procName, reader, params);
        }
    }


    private static void jdbcCall(Connection conn, String procName, RSReader reader, final Param... params) throws SQLException {

        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            final String sql = JDBCUtil.generateSqlCallString(procName, params.length);
            stmt = conn.prepareCall(sql);
            for (int i = 0; i < params.length; i++) {
                params[i].set(stmt, i + 1);
            }
            stmt.execute();
            // final long start = System.currentTimeMillis();
            for (int i = 0; i < params.length; i++) {
                params[i].get(stmt, i + 1);
            }
            rs = stmt.getResultSet();
            if (reader != null && rs != null) {
                int rows = reader.read(rs);
                // logRsBench(conn, start, sql, rows);
            }

        } finally {
            close(rs, stmt);
        }

    }

    public static void execute(final Connection connection, final String procName, final RSReader reader, final Param... params) throws SQLException {
        execute((Wrapper) connection, procName, reader, params);
    }

    public static void execute(final Connection connection, final String procName, final Param... params) throws SQLException {
        execute((Wrapper) connection, procName, null, params);
    }

    public static <V> Set<V> querySet(final Collection<DataSource> ds, final String procName, final RowMapper<V> mapper, final Param... params) {
        checkNotNull(mapper, "Row Mapper can't be null");
        SetRSReader<V> reader = new SetRSReader<V>(mapper);
        call(ds, procName, reader, params);
        return reader.getValue();
    }

    public static <V> Set<V> querySet(final DataSource ds, final String procName, final RowMapper<V> mapper, final Param... params) {
        return querySet(singleton(ds), procName, mapper, params);
    }

}
