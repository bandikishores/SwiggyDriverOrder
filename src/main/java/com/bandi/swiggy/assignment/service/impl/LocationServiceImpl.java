package com.bandi.swiggy.assignment.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.service.LocationService;

/**
 * 
 * Service responsible in handling location related services
 * 
 * @author kishore.bandi
 *
 */
@Service
public class LocationServiceImpl implements LocationService {

    private Integer maxAreaCode = 50000;

    /**
     * Dummy method which just returns the difference between the area code to identify the distance between locations.
     * In real world this would be done by calling a map API from google or other third party paid people or location
     * service.
     * 
     */
    // Kish - TODO : Call out in README
    @Override
    public double getDistanceBetweenLocations(LocationDTO src, LocationDTO dest) {
        return Math.abs(src.getAreaCode() - dest.getAreaCode());
    }

    @Override
    public LocationDTO getRandomLocation() {
        return new LocationDTO(ThreadLocalRandom.current().nextInt(maxAreaCode));
    }

}
