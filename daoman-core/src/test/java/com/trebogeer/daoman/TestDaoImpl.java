package com.trebogeer.daoman;

import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;

/**
 * @author dimav
 *         Date: 12/6/13
 *         Time: 3:13 PM
 */
public class TestDaoImpl {

    private final StoredProcedure testStoredProcedure;

    public TestDaoImpl(final DataSource dataSource) {
        this.testStoredProcedure = new StoredProcedure(dataSource, "test") {
        };

    }
}
