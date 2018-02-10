package com.bandi.swiggy.assignment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.dto.RestaurantDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.service.LocationService;
import com.bandi.swiggy.assignment.service.OrderService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * Service layer which mocks all calls to drivers.
 * 
 * @author kishore.bandi
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class OrderServiceEmulator implements OrderService {

    private List<OrderDTO>        unprocessedOrders = new ArrayList<>();

    private List<OrderDTO>        processedOrders   = new ArrayList<>();

    private Integer               orderId           = 1;

    private Integer               minWaitTimeInSec  = 0;

    private Integer               maxWaitTimeInSec  = 500;

    private final LocationService locationService;

    @Override
    public boolean createOrders(Integer numberOfOrders) throws OrderAssignmentException {
        if (numberOfOrders <= 0) {
            throw new OrderAssignmentException("Number of Orders specified is less than 0");
        }
        if (numberOfOrders > 500) {
            throw new OrderAssignmentException("Can't create more than 500 Orders");
        }
        for (int i = 0; i < numberOfOrders; i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(getNextOrderId());
            orderDTO.setOrderWaitingTimeInSec(getRandomWaitTime());
            orderDTO.setQuantity(1.0);
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setLocation(locationService.getRandomLocation());
            orderDTO.setRestaurant(restaurant);
            unprocessedOrders.add(orderDTO);
        }
        return true;
    }

    private Integer getNextOrderId() {
        return orderId++;
    }

    private Integer getRandomWaitTime() {
        return ThreadLocalRandom.current().nextInt(maxWaitTimeInSec - minWaitTimeInSec + 1) + minWaitTimeInSec;
    }

    public void assignOrderToDriver(OrderDTO order, DriverDTO driver) {
        order.setDriver(driver);
        unprocessedOrders.remove(order);
        processedOrders.add(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = new ArrayList<>();
        orderList.addAll(unprocessedOrders);
        orderList.addAll(processedOrders);
        return orderList;
    }

    @Override
    public List<OrderDTO> getUnprocessedOrdersForLocn(LocationDTO locn) {
        return unprocessedOrders;
    }

}
