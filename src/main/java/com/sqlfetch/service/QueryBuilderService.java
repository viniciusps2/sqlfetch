package com.sqlfetch.service;

import com.sqlfetch.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryBuilderService {

    public String buildQuery(Config config) {
        List<String> selectList = new ArrayList<>();
        
        // Build SELECT clause with primary keys from all tables
        for (Table table : config.getTables()) {
            for (String key : table.getPrimaryKeys()) {
                String field = String.format("%s.%s AS %s@%s", 
                    table.getName(), key, table.getName(), key);
                selectList.add(field);
            }
        }
        
        // Build WHERE clause with fetch keys
        List<String> keysList = new ArrayList<>();
        for (FetchKey key : config.getFetch().getKeys()) {
            String condition = String.format("%s.%s = '%s'", 
                config.getFetch().getTable(), key.getName(), key.getValue());
            keysList.add(condition);
        }
        
        // Build JOIN clauses
        List<String> joinList = new ArrayList<>();
        if (config.getJoins() != null) {
            for (Join join : config.getJoins()) {
                List<String> whereList = new ArrayList<>();
                for (JoinKey key : join.getKeys()) {
                    String condition = String.format("%s.%s = %s.%s", 
                        join.getRightTable(), key.getRight(), 
                        join.getLeftTable(), key.getLeft());
                    whereList.add(condition);
                }
                String joinText = String.format("LEFT JOIN %s ON %s", 
                    join.getRightTable(), String.join(" AND ", whereList));
                joinList.add(joinText);
            }
        }
        
        // Combine all parts
        String select = String.join(", ", selectList);
        String from = config.getFetch().getTable();
        String joins = String.join(" ", joinList);
        String where = String.join(" AND ", keysList);
        
        if (joins.isEmpty()) {
            return String.format("SELECT %s FROM %s WHERE %s", select, from, where);
        } else {
            return String.format("SELECT %s FROM %s %s WHERE %s", select, from, joins, where);
        }
    }
}
