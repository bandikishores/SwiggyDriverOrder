package com.bandi.swiggy.assignment.filter.impl;

import org.springframework.stereotype.Component;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.FilterQualifier;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.filter.IFilter;

/**
 * 
 * If an order-driver needs to be dropped from picking up then this should be implemented.
 * 
 * @author kishore.bandi
 *
 */
@Component
@FilterQualifier
public class DummyFilterImpl implements IFilter {

    @Override
    public boolean isEligible(DriverDTO driver, OrderDTO order) {
        return true;
    }

}
