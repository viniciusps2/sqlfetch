package com.sqlfetch.model;

import java.util.List;

public class Join {
    private String leftTable;
    private String rightTable;
    private List<JoinKey> keys;

    public Join() {
    }

    public Join(String leftTable, String rightTable, List<JoinKey> keys) {
        this.leftTable = leftTable;
        this.rightTable = rightTable;
        this.keys = keys;
    }

    public String getLeftTable() {
        return leftTable;
    }

    public void setLeftTable(String leftTable) {
        this.leftTable = leftTable;
    }

    public String getRightTable() {
        return rightTable;
    }

    public void setRightTable(String rightTable) {
        this.rightTable = rightTable;
    }

    public List<JoinKey> getKeys() {
        return keys;
    }

    public void setKeys(List<JoinKey> keys) {
        this.keys = keys;
    }
}
