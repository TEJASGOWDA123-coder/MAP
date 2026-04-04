package com.example.demo.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class LocationSimulator {

    @Autowired
    private SimpMessagingTemplate template;

    Random random = new Random();

    String[] parcelIds = { "P001", "P002", "P003" };

    @Scheduled(fixedRate = 1000)
    public void sendLocation() {

        for (String id : parcelIds) {

            double lat = 12.9716 + (random.nextDouble() - 0.5) * 0.02;
            double lng = 77.5946 + (random.nextDouble() - 0.5) * 0.02;

            Map<String, Object> data = new HashMap<>();
            data.put("parcelId", id);
            data.put("coordinates", new double[] { lng, lat });
            data.put("timestamp", System.currentTimeMillis());

            template.convertAndSend("/topic/locations", data);
        }
    }
}
