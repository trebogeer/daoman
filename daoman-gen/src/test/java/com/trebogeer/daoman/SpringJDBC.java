package com.trebogeer.daoman;

import org.junit.Test;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;


/**
 * @author dimav
 *         Date: 12/6/13
 *         Time: 3:10 PM
 */
/*
  Kind of ugly interaction with DB. Not even sure if I should consider it for implementation.
  Better take a look ot ibatis or smth else.
 */
public class SpringJDBC {


    @Test
    public void test() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/dinosaurs?zeroDateTimeBehavior=convertToNull&cachePrepStmts=true&cacheCallableStmts=true&prepStmtCacheSize=100&maintainTimeStats=false");
        dataSource.setUsername("daoman");
        dataSource.setPassword("daoman");

        DinosaursProcedure dinosaursProcedure = new DinosaursProcedure(dataSource);
        Map<String, Object> map = dinosaursProcedure.execute();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            System.out.println(stringObjectEntry);
        }
    }


    private static class DinosaursProcedure extends StoredProcedure {

        private final static String SP_NAME = "get_dinosaurs";

        private DinosaursProcedure(DataSource ds) {
            setDataSource(ds);
            setFunction(false);
            setSql(SP_NAME);
            declareParameter(new SqlOutParameter("error_code", Types.INTEGER));
            compile();
        }
    }

}
