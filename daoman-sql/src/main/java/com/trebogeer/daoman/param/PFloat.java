package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.trebogeer.daoman.jdbc.PUtils.getFloatOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setFloatOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.FLOAT;

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
        if (type == IN) {
            setFloatOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, FLOAT);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, FLOAT);
            if (value != null) {
                setFloatOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getFloatOrNull(pos, stmt);
    }
}
