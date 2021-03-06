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
import com.bandi.swiggy.assignment.service.impl.DriverServiceEmulator;

/**
 * 
 * This component criteria is responsible for Driver Free Time calculation
 * 
 * @author kishore.bandi
 *
 */
@Component
@CriteriaQualifier
public class DriverFreeTimeCriteria implements ICriteria {

    private CriteriaConfiguration configuration = getDefaultConfiguration();

    @Autowired
    public DriverFreeTimeCriteria(CriteriaManager criteriaManager) throws OrderAssignmentException {
        super();
        criteriaManager.register(getCriteriaType(), this);
    }

    private CriteriaConfiguration getDefaultConfiguration() {
        return new CriteriaConfiguration(getCriteriaType(), 1.0);
    }

    /**
     * Negate the actual value with Max. value so this criteria will get higher precedence.
     */
    @Override
    public Double getCriteriaScore(DriverDTO driver, OrderDTO order) {
        return (DriverServiceEmulator.MAX_WAIT_TIME_IN_SEC - driver.getDriverFreeTimeInSec())
                * configuration.getValue();
    }

    @Override
    public void reloadConfiguration(CriteriaConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Criteria getCriteriaType() {
        return Criteria.DRIVER_FREE_TIME;
    }

}
