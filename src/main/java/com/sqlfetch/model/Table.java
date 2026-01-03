package com.sqlfetch.model;

import java.util.List;

public class Table {
    private String name;
    private List<String> primaryKeys;

    public Table() {
    }

    public Table(String name, List<String> primaryKeys) {
        this.name = name;
        this.primaryKeys = primaryKeys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
}
