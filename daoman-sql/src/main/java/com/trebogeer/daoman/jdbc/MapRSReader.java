package com.trebogeer.daoman.jdbc;

import org.javatuples.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:52 PM
 */
public class MapRSReader<K, V> extends RSReader<Pair<K, V>, Map<K, V>> {

    protected MapRSReader(RowMapper<Pair<K, V>> mapper) {
        super(mapper);
    }

    @Override
    public int read(ResultSet rs) throws SQLException {
        return 0;
    }

    @Override
    public Map<K, V> getValue() {
        return null;
    }
}
