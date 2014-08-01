package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.trebogeer.daoman.jdbc.PUtils.getLongOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setLongOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.BIGINT;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:12 PM
 */
public class PLong extends Param<Long> {
    public PLong(Long value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setLongOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, BIGINT);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, BIGINT);
            if (value != null) {
                setLongOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getLongOrNull(pos, stmt);
    }
}
