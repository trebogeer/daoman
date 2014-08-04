package com.trebogeer.daoman.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:58 PM
 */
public class PojoRSReader<T> extends RSReader<T, T> {

    protected PojoRSReader(RowMapper<T> mapper) {
        super(mapper);
    }

    @Override
    public int read(ResultSet rs) throws SQLException {

        if (rs.next())
            value = mapper.map(0, rs);
        return value != null ? 1 : 0;
    }

}
