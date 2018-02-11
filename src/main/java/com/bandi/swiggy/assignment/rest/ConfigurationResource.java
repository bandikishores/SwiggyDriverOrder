package com.bandi.swiggy.assignment.rest;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bandi.swiggy.assignment.constants.Constants;
import com.bandi.swiggy.assignment.dto.CriteriaConfiguration;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.manager.CriteriaManager;
import com.bandi.swiggy.assignment.service.DriverService;
import com.bandi.swiggy.assignment.service.OrderService;

import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling order related UI Calls
 * 
 * @author kishore.bandi
 *
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class ConfigurationResource {

    private final CriteriaManager criteriaManager;

    private final DriverService   driverService;

    private final OrderService    orderService;

    @RequestMapping(method = { RequestMethod.POST}, path = "/configuration/criteria")
    public String createOrders(@RequestBody List<CriteriaConfiguration> criteriaConfiguration)
            throws OrderAssignmentException {
        if (CollectionUtils.isNotEmpty(criteriaConfiguration)) {
            criteriaManager.reloadConfigurations(criteriaConfiguration);
            return Constants.SUCCESS;
        } else {
            return Constants.FAILED;
        }
    }

    @RequestMapping(method = { RequestMethod.POST}, path = "/configuration/reset")
    public String reset() throws OrderAssignmentException {
        driverService.reset();
        orderService.reset();
        return Constants.SUCCESS;
    }

}
