package sise.cch.enums;

/**
 * @author Chench
 * @date 2021/8/31 17:42
 * @description
 */
public enum CommomType {

    SELECT("select"),
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");
    private String tyep;

    CommomType(String tyep) {
        this.tyep = tyep;
    }

    public String getTyep() {
        return tyep;
    }

    public void setTyep(String tyep) {
        this.tyep = tyep;
    }
}
