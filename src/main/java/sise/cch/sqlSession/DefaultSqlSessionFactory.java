package sise.cch.sqlSession;

import sise.cch.pojo.Configuration;

/**
 * @author Chench
 * @date 2021/8/31 10:36
 * @description
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
