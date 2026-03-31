package com.example.demo;

import com.example.demo.model.Parcel;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.service.ParcelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GeospatialTest {

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testNearbyQuery() {
        double lng = 77.5946;
        double lat = 12.9716;

        // Ensure data exists
        if (parcelRepository.count() == 0) {
            Parcel parcel = new Parcel();
            parcel.setParcelId("P001");
            parcel.setStatus("In Transit");
            parcel.setCurrentLocation(new GeoJsonPoint(lng, lat));
            parcelRepository.save(parcel);
        }

        List<Parcel> all = parcelRepository.findAll();
        System.out.println("All parcels: " + all.size());

        List<Parcel> nearby = parcelService.getNearbyParcels(lng, lat);
        System.out.println("Nearby parcels: " + nearby.size());

        assertThat(all).isNotEmpty();
        assertThat(nearby).isNotEmpty();
    }
}
