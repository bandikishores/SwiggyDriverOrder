package com.bandi.swiggy.assignment.processor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.service.OrderService;
import com.bandi.swiggy.assignment.service.impl.AssignmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Class responsible for processing orders and their locations
 * 
 * @author kishore.bandi
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class OrderProcessor {

    private static ReentrantLock    processingLock = new ReentrantLock();

    private final OrderService      orderService;

    private final AssignmentService assignmentService;

    /**
     * 
     * Assuming this will be called per location and there will be APIs to get orders based on location
     * 
     * @param locn
     * @return
     * @throws OrderAssignmentException
     */
    public Map<OrderDTO, DriverDTO> processOpenOrdersForLocation(LocationDTO locn) throws OrderAssignmentException {
        if (processingLock.isLocked()) {
            throw new OrderAssignmentException("Order for this area is already in progress!!");
        }

        try {
            processingLock.lock();
            List<OrderDTO> unprocessedOrdersForLocn = orderService.getUnprocessedOrdersForLocn(locn);
            log.info("Total Orders found for processing {}", unprocessedOrdersForLocn.size());

            return assignmentService.assignDriversToOrders(unprocessedOrdersForLocn, locn);
        } finally {
            processingLock.unlock();
        }
    }
}
