package sise.cch.sqlSession;

import sise.cch.pojo.Configuration;

/**
 * @author Chench
 * @date 2021/8/31 10:33
 * @description
 */
public interface SqlSessionFactory {

    public SqlSession openSession();
}
