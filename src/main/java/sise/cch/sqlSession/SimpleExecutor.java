package sise.cch.sqlSession;

import jdk.nashorn.internal.runtime.logging.Logger;
import sise.cch.config.BoundSql;
import sise.cch.pojo.Configuration;
import sise.cch.pojo.MappedStatement;
import sise.cch.utils.GenericTokenParser;
import sise.cch.utils.ParameterMapping;
import sise.cch.utils.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 15:08
 * @description
 */
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception{
        //获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //获取转换后的sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //预编译sql
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //设置参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClass(paramterType);

        List<ParameterMapping> parameterList = boundSql.getParameterList();
        parameterList.forEach(item -> {
            String content = item.getContent();
            //利用反射设置参数
            Field declaredField = null;
            try {
                declaredField = paramterTypeClass.getDeclaredField(content);
                //私有属性，暴力访问
                declaredField.setAccessible(true);
                Object o = declaredField.get(param[0]);
                preparedStatement.setObject(parameterList.indexOf(item)+1,o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClass(resultType);

        List<Object> objects = new ArrayList<>();

        while (resultSet.next()){
            Object o = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //获取字段名
                String columnName = metaData.getColumnName(i);
                //获取值
                Object value = resultSet.getObject(columnName);
                /**
                 * PropertyDescriptor类：(属性描述器)
                 * 　　PropertyDescriptor类表示JavaBean类通过存储器导出一个属性。主要方法：
                 * 　　1. getPropertyType()，获得属性的Class对象；
                 * 　　2. getReadMethod()，获得用于读取属性值的方法；
                 * 　　3. getWriteMethod()，获得用于写入属性值的方法；
                 * 　　4. hashCode()，获取对象的哈希值；
                 * 　　5. setReadMethod(Method readMethod)，设置用于读取属性值的方法；
                 * 　　6. setWriteMethod(Method writeMethod)，设置用于写入属性值的方法
                 */
                //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            objects.add(o);
        }

        return (List<E>) objects;
    }

    private Class<?> getClass(String paramterType) throws ClassNotFoundException{
        if(paramterType != null){
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }else {
            return null;
        }
    }

    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql){
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        //解析出来的sql
        String sqlText = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(sqlText, parameterMappings);
        return boundSql;
    }

    @Override
    public void insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //获取连接
        DataSource dataSource = configuration.getDataSource();
        Connection connection = dataSource.getConnection();

        //获取转换后的sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //预编译sql
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //设置参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> parameterTypeClass = getClass(paramterType);

        List<ParameterMapping> parameterList = boundSql.getParameterList();
        for (int i = 0; i < parameterList.size(); i++) {
            String content = parameterList.get(i).getContent();

            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }

        int i = preparedStatement.executeUpdate();
        System.out.println(i);
    }

    @Override
    public void update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //获取连接
        DataSource dataSource = configuration.getDataSource();
        Connection connection = dataSource.getConnection();

        //
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClass(paramterType);

        List<ParameterMapping> parameterList = boundSql.getParameterList();
        for (int i = 0; i < parameterList.size(); i++) {
            String content = parameterList.get(i).getContent();

            Field declaredField = paramterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1,o);
        }

        int i = preparedStatement.executeUpdate();
        System.out.println(i);
    }

    @Override
    public void deleteById(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        DataSource dataSource = configuration.getDataSource();
        Connection connection = dataSource.getConnection();

        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClass(paramterType);

        List<ParameterMapping> parameterList = boundSql.getParameterList();
        for (int i = 0; i < parameterList.size(); i++) {
            String content = parameterList.get(i).getContent();

            //判断参数是否是基本类型或基本类型包装类或String，
            // 如果是，不需要通过反射赋值，如果不是，则需要通过反射赋值
            if(isCommonDataType(paramterTypeClass) || isWrapClass(paramterTypeClass)
                    || paramterTypeClass.newInstance() instanceof String){
                preparedStatement.setObject(i+1,params[i]);
                break;
            }else if(paramterTypeClass instanceof Object){
                Field declaredField = paramterTypeClass.getDeclaredField(content);
                declaredField.setAccessible(true);
                Object o = declaredField.get(params[0]);
                preparedStatement.setObject(i+1,o);
                break;
            }

        }

        int i = preparedStatement.executeUpdate();
        System.out.println(i);
    }

    /**
     * 判断是否是基础数据类型，即 int,double,long等类似格式
     */
    public static boolean isCommonDataType(Class clazz){
        return clazz.isPrimitive();
    }

    /**
     * 判断是否是基础数据类型的包装类型
     *
     * @param clz
     * @return
     */
    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
