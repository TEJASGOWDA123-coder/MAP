package com.example.demo.service;

import com.example.demo.model.Parcel;
import com.example.demo.repository.ParcelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ParcelRepository repository;
    private final MongoTemplate mongoTemplate;

    public DataSeeder(ParcelRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ensure 2dsphere index exists
        mongoTemplate.indexOps(Parcel.class)
                .ensureIndex(new GeospatialIndex("currentLocation").typed(GeoSpatialIndexType.GEO_2DSPHERE));
        System.out.println("Ensured 2dsphere index on currentLocation.");

        if (repository.count() == 0) {
            Parcel parcel = new Parcel();
            parcel.setParcelId("P001");
            parcel.setStatus("In Transit");
            parcel.setCurrentLocation(new GeoJsonPoint(77.5946, 12.9716));
            repository.save(parcel);
            System.out.println("Seeded sample parcel data.");
        }
    }
}
