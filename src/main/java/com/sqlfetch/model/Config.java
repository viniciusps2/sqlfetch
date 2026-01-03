package com.sqlfetch.model;

import java.util.List;

public class Config {
    private List<Table> tables;
    private List<Join> joins;
    private Fetch fetch;

    public Config() {
    }

    public Config(List<Table> tables, List<Join> joins, Fetch fetch) {
        this.tables = tables;
        this.joins = joins;
        this.fetch = fetch;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Join> getJoins() {
        return joins;
    }

    public void setJoins(List<Join> joins) {
        this.joins = joins;
    }

    public Fetch getFetch() {
        return fetch;
    }

    public void setFetch(Fetch fetch) {
        this.fetch = fetch;
    }
}
