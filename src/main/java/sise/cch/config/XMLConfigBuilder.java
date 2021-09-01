package sise.cch.config;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sise.cch.io.Resources;
import sise.cch.pojo.Configuration;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author Chench
 * @date 2021/8/31 10:41
 * @description
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parse(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        Element element = document.getRootElement();
        //读取配置文件中所有“property”标签
        List<Element> propertys = element.selectNodes("//property");
        Properties properties = new Properties();
        propertys.forEach(item -> {
            String name = item.attributeValue("name");
            String value = item.attributeValue("value");
            properties.put(name,value);
        });

        //新建数据源
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        //将datasource封装到configuration
        configuration.setDataSource(comboPooledDataSource);

        //解析配置文件中的mapper
        List<Element> mappers = element.selectNodes("//mapper");
        mappers.forEach(mapper -> {
            String mapperPath = mapper.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            try {
                xmlMapperBuilder.parseMapper(resourceAsSteam);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        });

        return configuration;
    }
}
