package com.example.demo.controller;

import com.example.demo.model.Parcel;
import com.example.demo.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcels")
@CrossOrigin
public class ParcelController {

    @Autowired
    private ParcelService service;

    @GetMapping("/nearby")
    public List<Parcel> getNearbyParcels(
            @RequestParam double lng,
            @RequestParam double lat) {

        return service.getNearbyParcels(lng, lat);
    }
}
