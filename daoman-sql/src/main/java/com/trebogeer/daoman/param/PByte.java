package com.trebogeer.daoman.param;

import com.trebogeer.daoman.jdbc.PUtils;

import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.trebogeer.daoman.jdbc.PUtils.setByteOrNull;
import static com.trebogeer.daoman.param.Param.Type.IN;
import static com.trebogeer.daoman.param.Param.Type.INOUT;
import static com.trebogeer.daoman.param.Param.Type.OUT;
import static java.sql.Types.TINYINT;

/**
 * @author dimav
 *         Date: 6/5/13
 *         Time: 4:45 PM
 */
public class PByte extends Param<Byte> {
    public PByte(Byte value, Type type) {
        super(value, type);
    }

    @Override
    public void set(CallableStatement stmt, int pos) throws SQLException {
        if (type == IN) {
            setByteOrNull(pos, value, stmt);
        } else if (type == OUT) {
            stmt.registerOutParameter(pos, TINYINT);
        } else if (type == INOUT) {
            stmt.registerOutParameter(pos, TINYINT);
            if (value != null) {
                setByteOrNull(pos, value, stmt);
            }
        }
    }

    @Override
    public void get(CallableStatement stmt, int pos) throws SQLException {
        if (type.isOut())
            value = PUtils.getByteOrNull(pos, stmt);
    }
}
