package com.bandi.swiggy.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer       id;

    private RestaurantDTO restaurant;

    private ItemDTO       item;

    private Double        quantity;

    private Integer       orderWaitingTimeInSec;

    private DriverDTO     driver;
}
