package com.trebogeer.daoman.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:33 PM
 */
public abstract class RSReader<T, V> {

    protected final RowMapper<T> mapper;

    protected RSReader(RowMapper<T> mapper) {
        checkNotNull(mapper, "Row Mapper can't be null");
        this.mapper = mapper;
    }

    /**
     * @param rs
     * @return number of rows
     * @throws java.sql.SQLException
     */
    public abstract int read(ResultSet rs) throws SQLException;
    public abstract V getValue();


}
