package com.trebogeer.daoman.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:51 PM
 */
public class ListRSReader<T> extends RSReader<T, List<T>> {

    protected ListRSReader(RowMapper<T> mapper) {
        super(mapper);
    }

    @Override
    public int read(ResultSet rs) throws SQLException {
        //TODO implement
        return 0;
    }

    @Override
    public List<T> getValue() {
        return null;
    }
}
