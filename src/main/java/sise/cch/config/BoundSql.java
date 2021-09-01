package sise.cch.config;

import sise.cch.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 15:14
 * @description
 */
public class BoundSql {

    private String sqlText;
    private List<ParameterMapping> parameterList = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterList) {
        this.sqlText = sqlText;
        this.parameterList = parameterList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterMapping> parameterList) {
        this.parameterList = parameterList;
    }
}
