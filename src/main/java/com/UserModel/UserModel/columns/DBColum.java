package com.UserModel.UserModel.columns;

public class DBColum {
    private String name;
    private String table_name;
    private String type;

    public DBColum(String name, String table_name, String type) {
        this.name = name;
        this.table_name = table_name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getTable() {
        return table_name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTable(String table_name) {
        this.table_name = table_name;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DBColum{" +
                "name='" + name + '\'' +
                ", table='" + table_name + '\'' +
                ", type=" + type +
                '}';
    }
}
