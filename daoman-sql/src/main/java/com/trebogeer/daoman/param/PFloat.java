package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:44 PM
 */
public class PFloat extends Param<Float> {
    public PFloat(Float value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {

    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {

    }
}
