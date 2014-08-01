package com.trebogeer.daoman.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:45 PM
 */
public class CallbackRSReader<T> extends RSReader<T, Void> {

    private Callback<T> callback;

    protected CallbackRSReader(RowMapper<T> mapper, Callback<T> callback) {
        super(mapper);
        this.callback = callback;
    }

    @Override
    public int read(ResultSet rs) throws SQLException {
        // TODO implement
        return 0;
    }

    @Override
    public Void getValue() {
        return null;
    }
}
