package com.trebogeer.daoman.jdbc;

import org.javatuples.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:52 PM
 */
public class MapRSReader<K, V> extends RSReader<Pair<K, V>, Map<K, V>> {

    protected MapRSReader(RowMapper<Pair<K, V>> mapper) {
        super(mapper);
        value = new HashMap<K, V>();
    }

    @Override
    public int read(ResultSet rs) throws SQLException {
        int cnt = 0;
        for (int i = 0; rs.next(); i++) {
            final Pair<K, V> pair = mapper.map(i, rs);
            value.put(pair.getValue0(), pair.getValue1());
            cnt++;
        }
        return cnt;
    }

}
