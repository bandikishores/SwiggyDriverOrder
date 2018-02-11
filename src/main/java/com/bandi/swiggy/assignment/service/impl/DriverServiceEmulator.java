package com.bandi.swiggy.assignment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.service.DriverService;
import com.bandi.swiggy.assignment.service.LocationService;

import lombok.RequiredArgsConstructor;

/**
 * Service layer which mocks all calls to drivers.
 * 
 * @author kishore.bandi
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class DriverServiceEmulator implements DriverService {

    private List<DriverDTO>       availableDrivers     = new ArrayList<>();

    private List<DriverDTO>       unAvailableDrivers   = new ArrayList<>();

    private Integer               driverId             = 1;

    private Integer               minWaitTimeInSec     = 0;

    public static Integer         MAX_WAIT_TIME_IN_SEC = 500;

    private final LocationService LocationService;

    @Override
    public List<DriverDTO> getAvailableDrivers(LocationDTO orderLocation) {
        return availableDrivers;
    }

    @Override
    public boolean createDrivers(Integer numberOfDrivers) throws OrderAssignmentException {
        if (numberOfDrivers <= 0) {
            throw new OrderAssignmentException("Number of Drivers specified is less than 0");
        }
        if (numberOfDrivers > 500) {
            throw new OrderAssignmentException("Can't create more than 500 Drivers");
        }
        for (int i = 0; i < numberOfDrivers; i++) {
            availableDrivers.add(
                    new DriverDTO(getNextDriverId(), LocationService.getRandomLocation(), getRandomWaitTime(), true));
        }
        return true;
    }

    private Integer getNextDriverId() {
        return driverId++;
    }

    private Integer getRandomWaitTime() {
        return ThreadLocalRandom.current().nextInt(MAX_WAIT_TIME_IN_SEC - minWaitTimeInSec + 1) + minWaitTimeInSec;
    }

    @Override
    public synchronized void assignOrderToDriver(OrderDTO order, DriverDTO driver) {
        driver.setAvailable(false);
        availableDrivers.remove(driver);
        unAvailableDrivers.add(driver);
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        List<DriverDTO> driverList = new ArrayList<>();
        driverList.addAll(availableDrivers);
        driverList.addAll(unAvailableDrivers);
        return driverList;
    }

    @Override
    public synchronized void reset() {
        unAvailableDrivers.stream().forEach(driver -> {
            driver.setAvailable(true);
            availableDrivers.add(driver);
        });
        unAvailableDrivers.clear();

    }
}
