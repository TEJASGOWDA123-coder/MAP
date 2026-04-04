package com.example.demo.controller;

import com.example.demo.model.Parcel;
import com.example.demo.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcels")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:5173", "http://localhost:5174" })
public class ParcelController {

    @Autowired
    private ParcelService service;

    @GetMapping("/{id}/history")
    public List<com.example.demo.model.RouteLog> getParcelHistory(@PathVariable String id) {
        return service.getParcelHistory(id);
    }

    @GetMapping("/nearby")
    public List<Parcel> getNearbyParcels(
            @RequestParam double lng,
            @RequestParam double lat,
            @RequestParam(defaultValue = "5.0") double distance) {

        return service.getNearbyParcels(lng, lat, distance);
    }
}
