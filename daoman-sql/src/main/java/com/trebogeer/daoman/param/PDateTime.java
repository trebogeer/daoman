package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import static com.trebogeer.daoman.jdbc.PUtils.getDateTimeOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setDateTimeOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:44 PM
 */
public class PDateTime extends Param<Date> {
    public PDateTime(Date value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setDateTimeOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, Types.TIMESTAMP);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, Types.TIMESTAMP);
            if (value != null) {
                setDateTimeOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getDateTimeOrNull(pos, stmt);
    }
}
