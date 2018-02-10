package com.bandi.swiggy.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDriverScore {

    private Integer driverId;

    private Integer orderId;

    private Double  score;
}
