package com.bandi.swiggy.assignment.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.criteria.ICriteria;
import com.bandi.swiggy.assignment.dto.Criteria;
import com.bandi.swiggy.assignment.dto.CriteriaConfiguration;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;

/**
 * 
 * Service responsible for managing all the types of criteria present in system.
 * 
 * @author kishore.bandi
 *
 */
@Service
public class CriteriaManager {

    private final Map<Criteria, ICriteria> criteriaMapper = new HashMap<>();

    public void register(Criteria criteria, ICriteria iCriteria) throws OrderAssignmentException {
        if (criteriaMapper.containsKey(criteria)) {
            throw new OrderAssignmentException("Criteria already added {0} ", criteria);
        }
        criteriaMapper.put(criteria, iCriteria);
    }

    public ICriteria getCriteriaService(Criteria criteria) {
        return criteriaMapper.get(criteria);
    }

    public void reloadConfigurations(List<CriteriaConfiguration> configurations) {
        for (CriteriaConfiguration criteriaConfiguration : configurations) {
            getCriteriaService(criteriaConfiguration.getCriteria()).reloadConfiguration(criteriaConfiguration);
        }
    }
}
