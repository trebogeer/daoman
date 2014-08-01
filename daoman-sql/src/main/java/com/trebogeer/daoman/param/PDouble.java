package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.trebogeer.daoman.jdbc.PUtils.getDoubleOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setDoubleOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.DOUBLE;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:44 PM
 */
public class PDouble extends Param<Double> {
    public PDouble(Double value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setDoubleOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, DOUBLE);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, DOUBLE);
            if (value != null) {
                setDoubleOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getDoubleOrNull(pos, stmt);
    }
}
