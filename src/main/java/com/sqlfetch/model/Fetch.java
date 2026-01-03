package com.sqlfetch.model;

import java.util.List;

public class Fetch {
    private String table;
    private List<FetchKey> keys;

    public Fetch() {
    }

    public Fetch(String table, List<FetchKey> keys) {
        this.table = table;
        this.keys = keys;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<FetchKey> getKeys() {
        return keys;
    }

    public void setKeys(List<FetchKey> keys) {
        this.keys = keys;
    }
}
