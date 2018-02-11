package com.bandi.swiggy.assignment.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.dto.OrderDriverAssignmentDTO;
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
    public List<OrderDriverAssignmentDTO> createOrders(@PathVariable("locationId") Integer locationId)
            throws OrderAssignmentException {
        Map<OrderDTO, DriverDTO> openOrdersForLocation =
                orderProcessor.processOpenOrdersForLocation(new LocationDTO(locationId));
        
        if (MapUtils.isNotEmpty(openOrdersForLocation)) {
            return openOrdersForLocation.entrySet().stream().filter(en -> en.getKey().getDriver() != null)
                    .map(en -> new OrderDriverAssignmentDTO(en.getKey().getId(), en.getValue().getId(),
                            en.getKey().getRestaurant().getLocation().getAreaCode(),
                            en.getValue().getCurrentLocation().getAreaCode(), en.getKey().getOrderWaitingTimeInSec(),
                            en.getValue().getDriverFreeTimeInSec()))
                    .collect(Collectors.toList());
        }
        
        return new ArrayList<>();
    }

}
