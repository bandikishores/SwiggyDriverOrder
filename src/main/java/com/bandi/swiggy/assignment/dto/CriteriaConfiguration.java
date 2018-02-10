package com.bandi.swiggy.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaConfiguration {

    private Criteria criteria;

    private Double   value;
}
