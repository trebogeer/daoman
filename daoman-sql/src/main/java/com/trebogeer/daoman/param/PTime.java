package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:43 PM
 */
public class PTime extends Param<Date> {

    public PTime(Timestamp value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {

    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {

    }
}
