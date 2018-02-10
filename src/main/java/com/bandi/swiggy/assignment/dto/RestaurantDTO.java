package com.bandi.swiggy.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private Integer     id;

    private String      name;

    private LocationDTO location;
}
