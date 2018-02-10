package com.bandi.swiggy.assignment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bandi.swiggy.assignment.constants.Constants;
import com.bandi.swiggy.assignment.dto.OrderDTO;
import com.bandi.swiggy.assignment.exception.OrderAssignmentException;
import com.bandi.swiggy.assignment.service.OrderService;

import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling order related UI Calls
 * 
 * @author kishore.bandi
 *
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class OrderResource {

    private final OrderService orderService;

    @RequestMapping(method = { RequestMethod.POST}, path = "/orders/create/{numberOfOrders}")
    public String createOrders(@PathVariable("numberOfOrders") Integer numberOfOrders) throws OrderAssignmentException {
        if (orderService.createOrders(numberOfOrders)) {
            return Constants.SUCCESS;
        } else {
            return Constants.FAILED;
        }
    }

    @RequestMapping(method = { RequestMethod.GET}, path = "/orders")
    public List<OrderDTO> getOrders() throws OrderAssignmentException {
        return orderService.getAllOrders();
    }

}
