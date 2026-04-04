package com.example.demo.repository;

import com.example.demo.model.RouteLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RouteLogRepository extends MongoRepository<RouteLog, String> {
    List<RouteLog> findByParcelIdOrderByTimestampDesc(String parcelId);
}
