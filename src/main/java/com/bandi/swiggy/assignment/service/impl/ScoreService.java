package com.bandi.swiggy.assignment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.criteria.ICriteria;
import com.bandi.swiggy.assignment.dto.CriteriaQualifier;
import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;

/**
 * 
 * Service responsible in calculating the score based on filters and conditions
 * 
 * @author kishore.bandi
 *
 */
@Service
public class ScoreService {

    @Autowired
    @CriteriaQualifier
    private List<ICriteria> allCriterias;

    /**
     * 
     * Method which calculates the score for a given driver and order combination. Higher the score better is the driver
     * to order relation.
     * 
     * @param driver
     * @param order
     * @return
     */
    public double calculateScore(DriverDTO driver, OrderDTO order) {
        double score = 0;
        for (ICriteria iCriteria : allCriterias) {
            score += iCriteria.getCriteriaScore(driver, order);
        }
        return score;
    }

}
