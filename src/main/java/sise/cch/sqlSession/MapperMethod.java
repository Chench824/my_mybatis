package sise.cch.sqlSession;

import sise.cch.enums.CommomType;
import sise.cch.pojo.Configuration;
import sise.cch.pojo.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Chench
 * @date 2021/8/31 17:48
 * @description
 */
public class MapperMethod {

    private Configuration configuration;
    private SqlSession sqlSession;

    public MapperMethod(Configuration configuration,SqlSession sqlSession) {
        this.configuration = configuration;
        this.sqlSession = sqlSession;
    }

    public Object execute(String statementId, Method method, Object[] args) throws Exception{
        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        MappedStatement mappedStatement = mappedStatementMap.get(statementId);
        CommomType commomType = mappedStatement.getSqlTypeMap().get(statementId);
//        CommomType type = CommomType.valueOf(statementId);
        switch (commomType){
            case SELECT:
                Type genericReturnType = method.getGenericReturnType();
                if(genericReturnType instanceof ParameterizedType){
                    return sqlSession.selectList(statementId,args);
                }
                return sqlSession.selectOne(statementId,args);
            case INSERT:
                sqlSession.insert(statementId,args);
            case UPDATE:
                sqlSession.update(statementId,args);
            case DELETE:
                sqlSession.deleteById(statementId,args);
        }
        return null;
    }
}
