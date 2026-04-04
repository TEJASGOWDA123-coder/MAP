package com.example.demo.service;

import com.example.demo.model.RouteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RouteLogGenerator {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void generateLogs(int count) {
        // Clear existing logs to ensure clean performance test
        mongoTemplate.dropCollection("route_logs");

        Random random = new Random();
        List<RouteLog> logs = new ArrayList<>();
        String[] parcelIds = { "P001", "P002", "P003", "P004", "P005", "P006", "P007", "P008", "P009", "P010" };

        for (int i = 0; i < count; i++) {
            RouteLog log = new RouteLog();
            log.setParcelId(parcelIds[random.nextInt(parcelIds.length)]);
            log.setSpeed(40 + random.nextDouble() * 40); // 40-80 km/h
            log.setTimestamp(LocalDateTime.now().minusDays(1).plusSeconds(i));
            logs.add(log);

            if (logs.size() >= 10000) {
                mongoTemplate.insertAll(logs);
                logs.clear();
            }
        }
        if (!logs.isEmpty()) {
            mongoTemplate.insertAll(logs);
        }
    }
}
