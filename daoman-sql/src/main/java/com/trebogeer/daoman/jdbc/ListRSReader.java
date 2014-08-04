package com.trebogeer.daoman.jdbc;

import org.javatuples.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:51 PM
 */
public class ListRSReader<T> extends RSReader<T, List<T>> {

    protected ListRSReader(RowMapper<T> mapper) {
        super(mapper);
        value = new LinkedList<T>();
    }

    @Override
    public int read(ResultSet rs) throws SQLException {
        for (int i = 0; rs.next(); i++) {
            final T bean = mapper.map(i, rs);
            value.add(bean);
        }
        return value.size();
    }
}
