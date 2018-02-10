package com.bandi.swiggy.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer       id;

    private RestaurantDTO restaurant;

    @JsonIgnore
    private ItemDTO       item;

    @JsonIgnore
    private Double        quantity;

    private Integer       orderWaitingTimeInSec;

    private DriverDTO     driver;
}
