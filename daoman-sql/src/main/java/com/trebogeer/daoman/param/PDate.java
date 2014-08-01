package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import static com.trebogeer.daoman.jdbc.PUtils.getDateOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setDateOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:45 PM
 */
public class PDate extends Param<Date> {
    public PDate(Date value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setDateOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, Types.DATE);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, Types.DATE);
            if (value != null) {
                setDateOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getDateOrNull(pos, stmt);
    }
}
