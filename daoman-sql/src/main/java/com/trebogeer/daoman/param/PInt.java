package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.trebogeer.daoman.jdbc.PUtils.getIntegerOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setIntegerOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.INTEGER;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:44 PM
 */
public class PInt extends Param<Integer> {
    public PInt(Integer value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setIntegerOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, INTEGER);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, INTEGER);
            if (value != null) {
                setIntegerOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getIntegerOrNull(pos, stmt);
    }
}
