package sise.cch.sqlSession;

import org.dom4j.DocumentException;
import sise.cch.config.XMLConfigBuilder;
import sise.cch.pojo.Configuration;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author Chench
 * @date 2021/8/31 10:33
 * @description
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory bulid(InputStream inputStream) throws DocumentException, PropertyVetoException {
        // 第一：使用dom4j解析配置文件，将解析出来的内容封装到Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(inputStream);


        // 第二：创建sqlSessionFactory对象：工厂类：生产sqlSession:会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;

    }
}
