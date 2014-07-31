package com.trebogeer.daoman.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: Valya
 *         Date: 6/12/13
 *         Time: 8:30 PM
 */
public interface RowMapper<T> {

    /**
     * @param rowIndex
     *            - 0-based index of the current row in the result set
     * @param rsw
     *            - allows safe reading of the underlying ResultSet
     * @return instance of BeanT created from fields in the current row of the RSWrapperT object
     * @throws java.sql.SQLException
     */
    T map(final int rowIndex, final ResultSet rsw) throws SQLException;

}
