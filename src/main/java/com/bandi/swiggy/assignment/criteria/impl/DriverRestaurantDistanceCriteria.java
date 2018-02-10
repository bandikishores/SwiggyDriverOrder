package com.bandi.swiggy.assignment.criteria.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bandi.swiggy.assignment.criteria.ICriteria;
import com.bandi.swiggy.assignment.dto.Criteria;
import com.bandi.swiggy.assignment.dto.CriteriaConfiguration;
import com.bandi.swiggy.assignment.dto.CriteriaQualifier;
import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.manager.CriteriaManager;
import com.bandi.swiggy.assignment.service.LocationService;

/**
 * 
 * This component criteria is responsible for Driver and Restaurant Distance calculation
 * 
 * @author kishore.bandi
 *
 */
@Component
@CriteriaQualifier
public class DriverRestaurantDistanceCriteria implements ICriteria {

    private CriteriaConfiguration configuration = getDefaultConfiguration();

    private final LocationService locationService;

    @Autowired
    public DriverRestaurantDistanceCriteria(CriteriaManager criteriaManager, LocationService locationService)
            throws OrderAssignmentException {
        super();
        criteriaManager.register(getCriteriaType(), this);
        this.locationService = locationService;
    }

    private CriteriaConfiguration getDefaultConfiguration() {
        return new CriteriaConfiguration(getCriteriaType(), 0.1);
    }

    @Override
    public Double getCriteriaScore(DriverDTO driver, OrderDTO order) {
        double distanceBetweenLocations = locationService.getDistanceBetweenLocations(driver.getCurrentLocation(),
                order.getRestaurant().getLocation());
        return distanceBetweenLocations * configuration.getValue();
    }

    @Override
    public void reloadConfiguration(CriteriaConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Criteria getCriteriaType() {
        return Criteria.DRIVER_TO_RESTAURANT_DISTANCE;
    }

}
