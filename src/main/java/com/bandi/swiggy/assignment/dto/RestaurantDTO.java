package com.bandi.swiggy.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    @JsonIgnore
    private Integer     id;

    @JsonIgnore
    private String      name;

    private LocationDTO location;
}
