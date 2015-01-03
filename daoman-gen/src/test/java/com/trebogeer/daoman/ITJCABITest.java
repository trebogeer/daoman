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

    @Test(groups = {"itest"}, enabled = false)
    /*
    Ignored due to GET_GENERATED_KEYS issue with mysql. Need to take a look
     */
    public void test() throws Exception {
        MysqlDataSource source = new MysqlConnectionPoolDataSource();
        source.setUser(USER);
        source.setPassword(PWD);
        source.setServerName("localhost");
        source.setPort(Integer.valueOf(PORT));
        source.setDatabaseName(DB);

        new JdbcSession(source)
                .sql("CREATE TABLE foo (name VARCHAR(30))")
                .execute();

        final long id = new JdbcSession(source)
                .sql("INSERT INTO foo (name) VALUES (?)")
                .set("Jeff Lebowski")
                //.set(35000)
                .update(new SingleOutcome<Long>(Long.class));

        String name = new JdbcSession(source)
                .sql("SELECT name FROM foo WHERE id = ?")
                .set(id)
                .select(new SingleOutcome<String>(String.class));
        System.out.println(name);
    }
}
