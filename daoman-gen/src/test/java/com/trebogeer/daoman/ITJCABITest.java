package com.trebogeer.daoman;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.testng.annotations.Test;

/**
 * Created by dimav on 12/29/14.
 */
public class ITJCABITest extends MySQLTest {

    @Test(groups = {"itest"})
    public void test() throws Exception {
        MysqlDataSource source = new MysqlConnectionPoolDataSource();
        source.setUser(USER);
        source.setPassword(PWD);
        source.setServerName("localhost");
        source.setPort(Integer.valueOf(PORT));
        source.setDatabaseName(DB);
        String name = new JdbcSession(source)
                .sql("SELECT name FROM foo WHERE id = ?")
                .set(123)
                .select(new SingleOutcome<String>(String.class));
        System.out.println(name);
    }
}
