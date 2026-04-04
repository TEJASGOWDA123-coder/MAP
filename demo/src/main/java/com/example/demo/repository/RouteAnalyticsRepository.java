package com.example.demo.repository;

import com.example.demo.model.RouteAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteAnalyticsRepository extends JpaRepository<RouteAnalytics, String> {
}
