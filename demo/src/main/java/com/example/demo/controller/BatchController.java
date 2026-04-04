package com.example.demo.controller;

import com.example.demo.service.RouteLogGenerator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job routeJob;

    @Autowired
    private com.example.demo.repository.RouteAnalyticsRepository resultsRepository;

    @Autowired
    private RouteLogGenerator generator;

    @PostMapping("/seed")
    public String seed() {
        generator.generateLogs(100000);
        return "100,000 logs generated successfully!";
    }

    @org.springframework.web.bind.annotation.GetMapping("/results")
    public java.util.List<com.example.demo.model.RouteAnalytics> getResults() {
        return resultsRepository.findAll();
    }

    @PostMapping("/run")
    public Map<String, Object> runBatch() throws Exception {
        long startTime = System.currentTimeMillis();

        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(routeJob, params);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("durationMs", duration);
        response.put("durationSeconds", duration / 1000.0);
        response.put("message", "Processed 100,000 records successfully.");

        return response;
    }
}
