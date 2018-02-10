package com.bandi.swiggy.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private Integer     id;

    private LocationDTO currentLocation;

    private Integer     driverFreeTimeInSec;

    private boolean     available = true;
}
