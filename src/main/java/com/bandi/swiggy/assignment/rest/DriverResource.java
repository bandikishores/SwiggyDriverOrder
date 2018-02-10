package com.bandi.swiggy.assignment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bandi.swiggy.assignment.constants.Constants;
import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.service.DriverService;

import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling driver related UI Calls
 * 
 * @author kishore.bandi
 *
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class DriverResource {

    private final DriverService driverService;

    @RequestMapping(method = { RequestMethod.POST}, path = "/drivers/create/{numberOfDrivers}")
    public String createDrivers(@PathVariable("numberOfDrivers") Integer numberOfDrivers)
            throws OrderAssignmentException {
        if (driverService.createDrivers(numberOfDrivers)) {
            return Constants.SUCCESS;
        } else {
            return Constants.FAILED;
        }
    }

    @RequestMapping(method = { RequestMethod.GET}, path = "/drivers")
    public List<DriverDTO> getDrivers() throws OrderAssignmentException {
        return driverService.getAllDrivers();
    }

}
