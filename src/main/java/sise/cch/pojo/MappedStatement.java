package sise.cch.pojo;

import sise.cch.enums.CommomType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chench
 * @date 2021/8/31 10:50
 * @description
 */
public class MappedStatement {

    private String id;
    private String resultType;
    private String paramterType;
    private String sql;
    private Map<String, CommomType> sqlTypeMap = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamterType() {
        return paramterType;
    }

    public void setParamterType(String paramterType) {
        this.paramterType = paramterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String, CommomType> getSqlTypeMap() {
        return sqlTypeMap;
    }

    public void setSqlTypeMap(Map<String, CommomType> sqlTypeMap) {
        this.sqlTypeMap = sqlTypeMap;
    }
}
