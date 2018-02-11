package com.bandi.swiggy.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDriverAssignmentDTO {

    private Integer orderId;

    private Integer driverId;

    private Integer orderLocation;

    private Integer driverLocation;

    private Integer orderWaitingTimeInSec;

    private Integer driverFreeTimeInSec;
}
