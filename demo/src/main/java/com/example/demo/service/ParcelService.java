package com.example.demo.service;

import com.example.demo.model.Parcel;
import com.example.demo.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public List<Parcel> getNearbyParcels(double longitude, double latitude) {
        return getNearbyParcels(longitude, latitude, 10.0); // 10km
    }

    public List<Parcel> getNearbyParcels(double longitude, double latitude, double distanceKm) {
        GeoJsonPoint point = new GeoJsonPoint(longitude, latitude);
        Distance distance = new Distance(distanceKm, Metrics.KILOMETERS);
        return parcelRepository.findByCurrentLocationNear(point, distance);
    }

    public Parcel saveParcel(Parcel parcel) {
        return parcelRepository.save(parcel);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }
}
