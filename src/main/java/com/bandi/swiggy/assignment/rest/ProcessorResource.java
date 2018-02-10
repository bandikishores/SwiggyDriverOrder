package com.bandi.swiggy.assignment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bandi.swiggy.assignment.constants.Constants;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.processor.OrderProcessor;

import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling order related UI Calls
 * 
 * @author kishore.bandi
 *
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class ProcessorResource {

    private final OrderProcessor orderProcessor;

    @RequestMapping(method = { RequestMethod.POST}, path = "/process/{locationId}")
    public String createOrders(@PathVariable("locationId") Integer locationId) throws OrderAssignmentException {
        orderProcessor.processOpenOrdersForLocation(new LocationDTO(locationId));
        return Constants.SUCCESS;
    }

}
