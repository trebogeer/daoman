package com.trebogeer.daoman.jdbc;

import com.google.common.collect.Multimap;
import org.javatuples.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:50 PM
 */
public class MultimapRSReader<K, V> extends RSReader<Pair<K, V>, Multimap<K, V>> {

    protected MultimapRSReader(RowMapper<Pair<K, V>> mapper) {
        super(mapper);
    }

    @Override
    public int read(ResultSet rs) throws SQLException {
        //TODO implement
        return 0;
    }

    @Override
    public Multimap<K, V> getValue() {
        return null;
    }
}
