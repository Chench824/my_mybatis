package sise.cch.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sise.cch.enums.CommomType;
import sise.cch.pojo.Configuration;
import sise.cch.pojo.MappedStatement;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 11:12
 * @description
 */
public class XmlMapperBuilder {

    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseMapper(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);

        //获取根节点
        Element rootElement = document.getRootElement();
        //获取命名空间
        String namespace = rootElement.attributeValue("namespace");

        //获取该配置文件下的所有</select>标签
        List<Element> selects = rootElement.selectNodes("//select");

        setMapperStatement(namespace,selects,CommomType.SELECT);
        /*selects.forEach(item -> {
            String id = item.attributeValue("id");
            String resultType = item.attributeValue("resultType");
            String paramterType = item.attributeValue("paramterType");
            String sql = item.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setSql(sql);
            String statementId = namespace +"."+ id;
            mappedStatement.getSqlTypeMap().put(statementId, CommomType.SELECT);
            configuration.getMappedStatementMap().put(statementId,mappedStatement);
        });*/

        //解析该配置文件下的所有</insert>标签
        List<Element> insertNodes = rootElement.selectNodes("//insert");
        setMapperStatement(namespace,insertNodes,CommomType.INSERT);
//        insertNodes.forEach(item -> {
//            String id = item.attributeValue("id");
//            String resultType = item.attributeValue("resultType");
//            String paramterType = item.attributeValue("paramterType");
//            String sql = item.getTextTrim();
//            MappedStatement mappedStatement = new MappedStatement();
//            mappedStatement.setId(id);
//            mappedStatement.setResultType(resultType);
//            mappedStatement.setParamterType(paramterType);
//            mappedStatement.setSql(sql);
//            String statementId = namespace +"."+ id;
//            mappedStatement.getSqlTypeMap().put(statementId,CommomType.INSERT);
//            configuration.getMappedStatementMap().put(statementId,mappedStatement);
//        });

        //解析该配置文件下所有</update>标签
        List<Element> updateNodes = rootElement.selectNodes("//update");
        setMapperStatement(namespace,updateNodes,CommomType.UPDATE);

        //解析该配置文件下所有得</delete>标签
        List<Element> deleteNodes = rootElement.selectNodes("//delete");
        setMapperStatement(namespace,deleteNodes,CommomType.DELETE);
    }

    public void setMapperStatement(String namespace,List<Element> list,CommomType commomType){
        list.forEach(item ->{
            String id = item.attributeValue("id");
            String resultType = item.attributeValue("resultType");
            String paramterType = item.attributeValue("paramterType");
            String sql = item.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setSql(sql);
            String statementId = namespace +"."+ id;
            mappedStatement.getSqlTypeMap().put(statementId,commomType);
            configuration.getMappedStatementMap().put(statementId,mappedStatement);
        });

    }
}
