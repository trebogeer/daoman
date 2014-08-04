package com.trebogeer.daoman.jdbc;

import com.google.common.collect.LinkedListMultimap;
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
        value = LinkedListMultimap.create();
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
