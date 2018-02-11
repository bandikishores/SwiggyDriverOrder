package com.bandi.swiggy.assignment.service;

import java.util.List;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;

/**
 * Service interface where all calls related to driver entity is made.
 * 
 * @author kishore.bandi
 *
 */
public interface DriverService {

    public List<DriverDTO> getAvailableDrivers(LocationDTO orderLocation);

    public boolean createDrivers(Integer numberOfDrivers) throws OrderAssignmentException;

    public void assignOrderToDriver(OrderDTO order, DriverDTO driver);

    public List<DriverDTO> getAllDrivers();
    
    // This is a hack used to undo the assignment for the same set of orders.
    public void reset();

}
