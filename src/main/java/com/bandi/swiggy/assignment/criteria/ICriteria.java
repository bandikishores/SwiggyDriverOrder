package com.bandi.swiggy.assignment.criteria;

import com.bandi.swiggy.assignment.dto.Criteria;
import com.bandi.swiggy.assignment.dto.CriteriaConfiguration;
import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;

public interface ICriteria {

    public Double getCriteriaScore(DriverDTO driver, OrderDTO order);

    public void reloadConfiguration(CriteriaConfiguration configuration);

    public Criteria getCriteriaType();
}
