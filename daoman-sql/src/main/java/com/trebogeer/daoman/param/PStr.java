package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:12 PM
 */
public class PStr extends Param<String> {
    public PStr(String value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {

    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {

    }
}
