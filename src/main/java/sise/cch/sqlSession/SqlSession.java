package sise.cch.sqlSession;

import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 10:35
 * @description
 */
public interface SqlSession {

    public <E> List<E> selectList(String statementId, Object...params) throws Exception;

    public <T> T selectOne(String statementId,Object...params) throws Exception;

    public <T> T getMapper(Class<?> mapperClass);

    public void insert(String statementId,Object...params) throws Exception;

    public void update(String statementId,Object...params) throws Exception;

    public void deleteById(String statementId,Object...params) throws Exception;
}
