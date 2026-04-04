package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RouteAnalytics {
    @Id
    private String parcelId;
    private double avgSpeed;
    private long totalRecords;
    private LocalDateTime processedAt;
}
