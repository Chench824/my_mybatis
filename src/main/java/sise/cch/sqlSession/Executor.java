package sise.cch.sqlSession;

import sise.cch.pojo.Configuration;
import sise.cch.pojo.MappedStatement;

import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 15:06
 * @description
 */
public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object...param) throws Exception;

    public void insert(Configuration configuration,MappedStatement mappedStatement,Object...params) throws Exception;

    public void update(Configuration configuration,MappedStatement mappedStatement,Object...params) throws Exception;

    public void deleteById(Configuration configuration,MappedStatement mappedStatement,Object...params) throws Exception;
}
