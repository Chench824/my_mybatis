package sise.cch.sqlSession;

import com.sun.deploy.net.proxy.ProxyHandler;
import sise.cch.pojo.Configuration;
import sise.cch.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 10:36
 * @description
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private SqlSession sqlSession;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.sqlSession = this;
    }

    public <E> List<E> selectList(String statementId,Object...param) throws Exception{
        Executor executor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = executor.query(configuration, mappedStatement, param);
        return (List<E>) list;
    }

    public <T> T selectOne(String statementId,Object...param) throws Exception{
        List<Object> list = selectList(statementId, param);
        if(list.size() == 1){
            return (T) list.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {

        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都还是去执行JDBC代码 //根据不同情况，来调用selctList或者selectOne
                // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className +"."+ methodName;


                MapperMethod mapperMethod = new MapperMethod(configuration,sqlSession);
                return mapperMethod.execute(statementId,method,args);
                /*//获取返回类型
                Type genericReturnType = method.getGenericReturnType();
                //判断返回类型是否有泛型
                if(genericReturnType instanceof ParameterizedType){
                    return selectList(statementId,args);
                }

                return selectOne(statementId,args);*/
            }
        });
        return (T) o;
    }

    @Override
    public void insert(String statementId, Object... params) throws Exception{
        Executor executor = new SimpleExecutor();
        executor.insert(configuration,configuration.getMappedStatementMap().get(statementId),params);
    }

    @Override
    public void update(String statementId, Object... params) throws Exception {
        Executor executor = new SimpleExecutor();
        executor.update(configuration,configuration.getMappedStatementMap().get(statementId),params);
    }

    @Override
    public void deleteById(String statementId, Object... params) throws Exception {
        Executor executor = new SimpleExecutor();
        executor.deleteById(configuration,configuration.getMappedStatementMap().get(statementId),params);
    }
}
