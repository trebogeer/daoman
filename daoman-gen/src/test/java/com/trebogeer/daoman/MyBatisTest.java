package com.trebogeer.daoman;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @author dimav
 *         Date: 8/22/14
 *         Time: 2:57 PM
 */
public class MyBatisTest {

    @Test
    public void runTest() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
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

