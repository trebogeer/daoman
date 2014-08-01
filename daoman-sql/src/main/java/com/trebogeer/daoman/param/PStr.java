package com.trebogeer.daoman.param;

import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.trebogeer.daoman.jdbc.PUtils.getStringOrNull;
import static com.trebogeer.daoman.jdbc.PUtils.setStringOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.VARCHAR;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:12 PM
 */
public class PStr extends Param<String> {
    public PStr(String value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setStringOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, VARCHAR);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, VARCHAR);
            if (value != null) {
                setStringOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = getStringOrNull(pos, stmt);
    }
}
