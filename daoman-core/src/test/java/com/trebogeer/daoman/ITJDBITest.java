package com.trebogeer.daoman;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;
import org.testng.annotations.Test;

/**
 * @author dimav
 *         Date: 12/26/14
 *         Time: 2:55 PM
 */
public class ITJDBITest extends MySQLTest {

    @Test(groups = {"itest"})
    public void test() throws Exception {
       /* Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://" + cfg.dbHost + ":" + cfg.dbPort + "/",
                cfg.userName, cfg.password);*/
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUser(USER);
        dataSource.setPassword(PWD);
        dataSource.setServerName("localhost");
        dataSource.setPort(Integer.valueOf(PORT));
        dataSource.setDatabaseName(DB);

        DBI dbi = new DBI(dataSource);


        Handle h = dbi.open();
        h.execute("create table something (id int primary key, name varchar(100))");

        h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");

        String name = h.createQuery("select name from something where id = :id")
                .bind("id", 1)
                .map(StringMapper.FIRST)
                .first();

        assert name.equals("Brian");

        h.close();
    }
}
