package com.bandi.swiggy.assignment.service;

import java.util.List;

import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.LocationDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;

public interface OrderService {
    public boolean createOrders(Integer numberOfOrders) throws OrderAssignmentException;

    public void assignOrderToDriver(OrderDTO order, DriverDTO driver);

    public List<OrderDTO> getAllOrders();

    public List<OrderDTO> getUnprocessedOrdersForLocn(LocationDTO locn);

    // This is a hack used to undo the assignment for the same set of orders.
    public void reset();
}
