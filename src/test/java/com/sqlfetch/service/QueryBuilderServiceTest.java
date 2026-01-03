package com.sqlfetch.service;

import com.sqlfetch.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QueryBuilderServiceTest {

    @Autowired
    private QueryBuilderService queryBuilderService;

    @Test
    public void testBuildQueryForOneTable() {
        Config config = new Config();
        config.setTables(Arrays.asList(
            new Table("configuration", Arrays.asList("id"))
        ));
        config.setFetch(new Fetch("configuration", 
            Arrays.asList(new FetchKey("id", "1"))));
        config.setJoins(Collections.emptyList());

        String query = queryBuilderService.buildQuery(config);
        String expected = "SELECT configuration.id AS configuration@id FROM configuration WHERE configuration.id = '1'";
        
        assertEquals(expected, query);
    }

    @Test
    public void testBuildQueryForTwoTables() {
        Config config = new Config();
        config.setTables(Arrays.asList(
            new Table("configuration", Arrays.asList("id"))
        ));
        config.setJoins(Arrays.asList(
            new Join("configuration", "feature_category", 
                Arrays.asList(new JoinKey("id", "configurationid")))
        ));
        config.setFetch(new Fetch("configuration", 
            Arrays.asList(new FetchKey("id", "1"))));

        String query = queryBuilderService.buildQuery(config);
        String expected = "SELECT configuration.id AS configuration@id FROM configuration LEFT JOIN feature_category ON feature_category.configurationid = configuration.id WHERE configuration.id = '1'";
        
        assertEquals(expected, query);
    }
}
