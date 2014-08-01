package com.trebogeer.daoman.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:46 PM
 */
public class SetRSReader<T> extends RSReader<T, Set<T>> {
    protected SetRSReader(RowMapper<T> mapper) {
        super(mapper);
    }

    @Override
    public int read(ResultSet rs) throws SQLException {
        return 0;
    }

    @Override
    public Set<T> getValue() {
        return null;
    }
}
