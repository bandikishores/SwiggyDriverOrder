package com.bandi.swiggy.assignment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.FilterQualifier;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.filter.IFilter;
import com.bandi.swiggy.assignment.service.DriverService;
import com.bandi.swiggy.assignment.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Class which handles how a orders should be processed per location.
 * 
 * @author kishore.bandi
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class AssignmentService {

    private final DriverService    driverService;

    private final OrderService     orderService;

    private final ScoreService     scoreService;

    private final AlgorithmService algorithmService;

    @Autowired
    @FilterQualifier
    private List<IFilter>          allFilters;

    /**
     * 
     * Method which returns the driverId-orderId mapping for the list of available orders with the same Location.
     * 
     * @throws OrderAssignmentException
     * 
     */
    public Map<OrderDTO, DriverDTO> assignDriversToOrders(List<OrderDTO> orders, LocationDTO orderLocation)
            throws OrderAssignmentException {
        if (CollectionUtils.isEmpty(orders)) {
            return new HashMap<>();
        }

        List<DriverDTO> availableDrivers = driverService.getAvailableDrivers(orderLocation);
        log.info("Total Drivers found for processing {}", availableDrivers.size());

        if (availableDrivers.size() == 0) {
            log.info("No Drivers found to process order");
            return new HashMap<>();
        }

        Map<OrderDTO, Map<DriverDTO, Double>> orderDriverScoreMap = generateScoreMap(orders, availableDrivers);
        Map<OrderDTO, DriverDTO> orderToDriverMapping = getOptimalOrderDriverByScore(orders, orderDriverScoreMap);

        updateOrdersAndDrivers(orderToDriverMapping);
        return orderToDriverMapping;
    }

    private void updateOrdersAndDrivers(Map<OrderDTO, DriverDTO> orderToDriverMapping) {
        for (Entry<OrderDTO, DriverDTO> entry : orderToDriverMapping.entrySet()) {
            if (entry.getValue() != null) {
                driverService.assignOrderToDriver(entry.getKey(), entry.getValue());
                orderService.assignOrderToDriver(entry.getKey(), entry.getValue());
            } else {
                log.info("Could not find any driver for order {}", entry.getKey());
            }
        }
    }

    /**
     * Provides the most optimal order-driver mapping by calling {@link AlgorithmService}
     * 
     * @param orders
     * @param orderDriverScoreMap
     * @return
     */
    private Map<OrderDTO, DriverDTO> getOptimalOrderDriverByScore(List<OrderDTO> orders,
            Map<OrderDTO, Map<DriverDTO, Double>> orderDriverScoreMap) {
        Map<OrderDTO, DriverDTO> orderToDriverMapping =
                algorithmService.getOptimalDriverOrderMapping(orderDriverScoreMap);
        for (OrderDTO order : orders) {
            if (!orderToDriverMapping.containsKey(order)) {
                orderToDriverMapping.put(order, null);
            }
        }
        return orderToDriverMapping;
    }

    /**
     * 
     * generates a score for each order-driver combo.
     * 
     * @param orders
     * @param availableDrivers
     * @return
     */
    private Map<OrderDTO, Map<DriverDTO, Double>> generateScoreMap(List<OrderDTO> orders,
            List<DriverDTO> availableDrivers) {
        Map<OrderDTO, Map<DriverDTO, Double>> orderDriverScoreMap = new HashMap<>();

        for (OrderDTO order : orders) {
            for (DriverDTO driver : availableDrivers) {
                if (!filter(order, driver)) {
                    continue;
                } else {
                    double score = scoreService.calculateScore(driver, order);
                    if (!orderDriverScoreMap.containsKey(order)) {
                        Map<DriverDTO, Double> driverMap = new HashMap<>();
                        orderDriverScoreMap.put(order, driverMap);
                    }
                    orderDriverScoreMap.get(order).put(driver, score);
                }
            }
        }
        return orderDriverScoreMap;
    }

    /**
     * 
     * calls all the filters registered, in order filter out any order or driver.
     * 
     * @param order
     * @param driver
     * @return
     */
    private boolean filter(OrderDTO order, DriverDTO driver) {
        Optional<IFilter> optionalFilter =
                allFilters.stream().filter(filter -> !filter.isEligible(driver, order)).findAny();
        if (optionalFilter.isPresent()) {
            log.error("Order {} and Driver {} is not eligible to be processed due to filter failure {} ", order, driver,
                    optionalFilter.get());
            return false;
        }
        return true;
    }
}
