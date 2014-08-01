package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.trebogeer.daoman.jdbc.PUtils.getShortOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setShortOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.SMALLINT;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:44 PM
 */
public class PShort extends Param<Short> {
    public PShort(Short value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setShortOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, SMALLINT);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, SMALLINT);
            if (value != null) {
                setShortOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getShortOrNull(pos, stmt);
    }
}
