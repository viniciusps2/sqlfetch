package com.sqlfetch.controller;

import com.sqlfetch.model.Config;
import com.sqlfetch.service.QueryBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    private QueryBuilderService queryBuilderService;

    @PostMapping("/build-query")
    public ResponseEntity<Map<String, String>> buildQuery(@RequestBody Config config) {
        String query = queryBuilderService.buildQuery(config);
        
        Map<String, String> response = new HashMap<>();
        response.put("query", query);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("virtualThreads", "enabled");
        response.put("threadInfo", Thread.currentThread().toString());
        
        return ResponseEntity.ok(response);
    }
}
