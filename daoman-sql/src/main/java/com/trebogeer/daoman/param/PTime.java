package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static com.trebogeer.daoman.jdbc.PUtils.getTimeOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setTimeOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.TIME;

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
        if (type == IN) {
            setTimeOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, TIME);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, TIME);
            if (value != null) {
                setTimeOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getTimeOrNull(pos, stmt);
    }
}
