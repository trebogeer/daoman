package com.trebogeer.daoman;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @author dimav
 *         Date: 8/22/14
 *         Time: 2:57 PM
 */
public class ITMyBatisTest extends MySQLTest {

    static final String config = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<!DOCTYPE configuration\n" +
            "        PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n" +
            "        \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n" +
            "<configuration>\n" +
            "    <environments default=\"development\">\n" +
            "        <environment id=\"development\">\n" +
            "            <transactionManager type=\"JDBC\"/>\n" +
            "            <dataSource type=\"POOLED\">\n" +
            "                <property name=\"driver\" value=\"com.mysql.jdbc.Driver\"/>\n" +
            "                <property name=\"url\" value=\"jdbc:mysql://localhost:3306/dinosaurs\"/>\n" +
            "                <property name=\"username\" value=\"daoman\"/>\n" +
            "                <property name=\"password\" value=\"daoman\"/>\n" +
            "            </dataSource>\n" +
            "        </environment>\n" +
            "    </environments>\n" +
            "    <!--<mappers>-->\n" +
            "        <!--<mapper resource=\"com/trebogeer/daoman/BlogMapper.xml\"/>-->\n" +
            "    <!--</mappers>-->\n" +
            "</configuration>";

    @Test(groups = {"itest"}, enabled = false)
    public void runTest() throws IOException {

        String c = config.replaceAll("3306", PORT);
        ByteArrayInputStream bais = new ByteArrayInputStream(c.getBytes());
       // String resource = "mybatis.xml";
       // InputStream inputStream = Resources.getResourceAsStream(resource);
        InputStream inputStream = bais;
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().addMapper(DinosaursMapper.class);
        SqlSession session = sqlSessionFactory.openSession(true);
        DinosaursMapper mapper = session.getMapper(DinosaursMapper.class);
        MBParam p = new MBParam();
        Collection<Long> e = mapper.getDinosaurs(p);

        System.out.println(p.getInteger());

        for (Long l : e) {
            System.out.println(l);
        }


        Assert.assertEquals(p.getInteger(), Integer.valueOf(0));

        session.close();
    }


}

