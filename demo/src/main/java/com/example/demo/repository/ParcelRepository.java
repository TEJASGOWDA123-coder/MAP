package com.example.demo.repository;

import com.example.demo.model.Parcel;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepository extends MongoRepository<Parcel, String> {
    List<Parcel> findByCurrentLocationNear(GeoJsonPoint point, double distance);
}
