package com.trebogeer.daoman;

import com.trebogeer.daoman.param.PByte;
import com.trebogeer.daoman.param.PDate;
import com.trebogeer.daoman.param.PDateTime;
import com.trebogeer.daoman.param.PDouble;
import com.trebogeer.daoman.param.PFloat;
import com.trebogeer.daoman.param.PInt;
import com.trebogeer.daoman.param.PLong;
import com.trebogeer.daoman.param.PShort;
import com.trebogeer.daoman.param.PStr;
import com.trebogeer.daoman.param.PTime;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import static java.sql.DatabaseMetaData.procedureColumnIn;
import static java.sql.DatabaseMetaData.procedureColumnInOut;
import static java.sql.DatabaseMetaData.procedureColumnOut;
import static java.sql.DatabaseMetaData.procedureNullable;

/**
 * @author dimav
 *         Date: 1/17/12
 *         Time: 12:11 PM
 */
class SQLParam {
    // alias column name
    private String columnAliasName;
    // in, out or inout
    private short type;
    private int jdbcType;
    private short nullable;

    // alias table name 
    private String tableAliasName = null;
    // original table name, could be empty if sub-select
    private String tableOriginalName = null;
    // original column name
    private String columnOriginalName = null;


    // need these 2 to figure out similar result sets
    private String dbTypeName = "";
    private int dbTypeDisplaySize = 0;

    public SQLParam(String name, short type, int jdbcType, short nullable) {
        this.columnAliasName = name;
        this.type = type;
        this.jdbcType = jdbcType;
        this.nullable = nullable;
    }

    public SQLParam(String name, short type, int jdbcType, short nullable, String tableName, String tableOriginalName, String columnOriginalName, String dbTypeName, int dbTypeDisplaySize) {
        this.columnAliasName = name;
        this.type = type;
        this.jdbcType = jdbcType;
        this.nullable = nullable;
        this.tableAliasName = tableName;
        this.tableOriginalName = tableOriginalName;
        this.columnOriginalName = columnOriginalName;
        this.dbTypeName = dbTypeName;
        this.dbTypeDisplaySize = dbTypeDisplaySize;
    }

    public boolean isIn() {
        return (type == procedureColumnIn);
    }

    public boolean isOut() {
        return (type == procedureColumnOut);
    }

    public String getDbTypeName() {
        return dbTypeName;
    }

    public int getDbTypeDisplaySize() {
        return dbTypeDisplaySize;
    }

    public boolean isInOut() {
        return (type == procedureColumnInOut);
    }

    public boolean isNullable() {
        return (nullable == procedureNullable);
    }

    public String getTableOriginalName() {
        return tableOriginalName;
    }

    public String getColumnName() {
        return columnOriginalName;
    }

    public int getType() {
        return jdbcType;
    }

    public String getName() {
        return columnAliasName;
    }

    public String getTableName() {
        return tableAliasName;
    }

    public Class<?> getJavaType() {
        switch (jdbcType) {
            case Types.VARCHAR:
                return String.class;
            case Types.BIGINT:
                return Long.class;
            case Types.TINYINT:
                return Byte.class;
            case Types.DATE:
                return Date.class;
            case Types.TIMESTAMP:
                return Date.class;
            case Types.DOUBLE:
                return Double.class;
            case Types.FLOAT:
                return Float.class;
            case Types.INTEGER:
                return Integer.class;
            case Types.SMALLINT:
                return Short.class;
            case Types.TIME:
                return Date.class;
            default:
                return String.class;
        }
    }

    public Class<?> getDaoManType() {
        switch (jdbcType) {
            case Types.VARCHAR:
                return PStr.class;
            case Types.BIGINT:
                return PLong.class;
            case Types.TINYINT:
                return PByte.class;
            case Types.DATE:
                return PDate.class;
            case Types.TIMESTAMP:
                return PDateTime.class;
            case Types.DOUBLE:
                return PDouble.class;
            case Types.FLOAT:
                return PFloat.class;
            case Types.INTEGER:
                return PInt.class;
            case Types.SMALLINT:
                return PShort.class;
            case Types.TIME:
                return PTime.class;
            default:
                return PStr.class;
        }
    }


    public String getParamMethodName() {
        switch (jdbcType) {
            case Types.VARCHAR:
                return getFullMethodName("Str");
            case Types.BIGINT:
                return getFullMethodName("Long");
            case Types.TINYINT:
                return getFullMethodName("Byte");
            case Types.DATE:
                return getFullMethodName("Date");
            case Types.TIMESTAMP:
                return getFullMethodName("DateTime");
            case Types.DOUBLE:
                return getFullMethodName("Double");
            case Types.FLOAT:
                return getFullMethodName("Float");
            case Types.INTEGER:
                return getFullMethodName("Int");
            case Types.SMALLINT:
                return getFullMethodName("Short");
            case Types.TIME:
                return getFullMethodName("Time");
            default:
                return getFullMethodName("Str");
        }
    }


    public String getResultSetWrapperMethodName() {
        switch (jdbcType) {
            case Types.VARCHAR:
                return "getString";
            case Types.BIGINT:
                return "getLongOrNull";
            case Types.TINYINT:
                return "getByteOrNull";
            case Types.DATE:
                return "getDateOrNull";
            case Types.TIMESTAMP:
                return "getDateTimeOrNull";
            case Types.DOUBLE:
                return "getDoubleOrNull";
            case Types.FLOAT:
                return "getFloatOrNull";
            case Types.INTEGER:
                return "getIntegerOrNull";
            case Types.SMALLINT:
                return "getShortOrNull";
            case Types.TIME:
                return "getTimeOrNull";
            default:
                return "getString";
        }
    }

    private String getFullMethodName(String name) {
        if (isIn()) {
            return "in" + name;
        } else if (isInOut()) {
            return "inOut" + name;
        } else {
            return "out" + name;
        }
    }

    public void setFakeParam(CallableStatement smtp, int i) throws SQLException {
        switch (jdbcType) {
            case Types.VARCHAR:
//                if (this.name != null && (this.name.contains("sort") || this.name.contains("order"))) {
//                    smtp.setString(i, "asc");
//                } else {
                smtp.setString(i, FakeInput.str());
                // }
                return;
            case Types.BIGINT:
                smtp.setLong(i, FakeInput.negLong());
                return;
            case Types.TINYINT:
                smtp.setByte(i, FakeInput.negByte());
                return;
            case Types.DATE:
                smtp.setDate(i, new java.sql.Date(FakeInput.date().getTime()));
                return;
            case Types.TIMESTAMP:
                smtp.setTimestamp(i, FakeInput.timestamp());
                return;
            case Types.DOUBLE:
                smtp.setDouble(i, FakeInput.negDouble());
                return;
            case Types.FLOAT:
                smtp.setFloat(i, FakeInput.negFloat());
                return;
            case Types.INTEGER:
                smtp.setInt(i, FakeInput.negInteger());
                return;
            case Types.SMALLINT:
                smtp.setShort(i, FakeInput.negShort());
                return;
            case Types.TIME:
                smtp.setTime(i, FakeInput.time());
                return;
            default:
                smtp.setString(i, FakeInput.str());
        }
    }

    @Override
    public String toString() {
        return "SQLParam{" +
                "name='" + columnAliasName + '\'' +
                ", type=" + type +
                ", jdbcType=" + jdbcType +
                ", nullable=" + nullable +
                ", tableName='" + tableAliasName + '\'' +
                ", tableOriginalName='" + tableOriginalName + '\'' +
                ", columnName='" + columnOriginalName + '\'' +
                ", dbTypeName='" + dbTypeName + '\'' +
                ", dbTypeDisplaySize=" + dbTypeDisplaySize +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SQLParam sqlParam = (SQLParam) o;

        if (dbTypeDisplaySize != sqlParam.dbTypeDisplaySize) return false;
        //   if (jdbcType != sqlParam.jdbcType) return false;
        //   if (nullable != sqlParam.nullable) return false;
        //   if (type != sqlParam.type) return false;
        if (columnOriginalName != null && columnOriginalName.length() != 0
                && sqlParam.columnOriginalName != null && sqlParam.columnOriginalName.length() != 0) {
            if (!columnOriginalName.equals(sqlParam.columnOriginalName))
                return false;
        } else if (columnAliasName != null ? !columnAliasName.equals(sqlParam.columnAliasName) : sqlParam.columnAliasName != null)
            return false;
        if (dbTypeName != null ? !dbTypeName.equals(sqlParam.dbTypeName) : sqlParam.dbTypeName != null) return false;

        if (tableOriginalName != null && tableOriginalName.length() != 0 && sqlParam.tableOriginalName != null && sqlParam.tableOriginalName.length() != 0 && !tableOriginalName.equals(sqlParam.tableOriginalName))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = columnAliasName != null ? columnAliasName.hashCode() : 0;
        result = 31 * result + (int) type;
        result = 31 * result + jdbcType;
        result = 31 * result + (int) nullable;
        result = 31 * result + (tableAliasName != null ? tableAliasName.hashCode() : 0);
        result = 31 * result + (tableOriginalName != null ? tableOriginalName.hashCode() : 0);
        result = 31 * result + (columnOriginalName != null ? columnOriginalName.hashCode() : 0);
        result = 31 * result + (dbTypeName != null ? dbTypeName.hashCode() : 0);
        result = 31 * result + dbTypeDisplaySize;
        return result;
    }
}
