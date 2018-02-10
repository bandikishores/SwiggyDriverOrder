package com.bandi.swiggy.assignment.service;

import com.bandi.swiggy.assignment.dto.LocationDTO;

public interface LocationService {

    public double getDistanceBetweenLocations(LocationDTO src, LocationDTO dest);

    public LocationDTO getRandomLocation();

}
