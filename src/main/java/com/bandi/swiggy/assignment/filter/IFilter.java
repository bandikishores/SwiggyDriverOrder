package com.bandi.swiggy.assignment.filter;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;

public interface IFilter {

    public boolean isEligible(DriverDTO driver, OrderDTO order);
}
