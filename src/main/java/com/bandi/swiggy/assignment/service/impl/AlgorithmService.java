package com.bandi.swiggy.assignment.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bandi.swiggy.assignment.algorithm.HungarianAlgorithm;
import com.bandi.swiggy.assignment.dto.DriverDTO;
import com.bandi.swiggy.assignment.dto.OrderDTO;

/**
 * 
 * Uses an Algorithm to find the most optimial pick between drivers & orders.
 * 
 * @author kishore.bandi
 *
 */
@Service
public class AlgorithmService {

    /*-
     * 
     * 1. Create an index to Object map for both Order & Driver. This is used for easier creation of Cost Matrix. 
     * 2. Generate the cost matrix 
     * 3. Call Hungarian Algorithm 
     * 4. create a Driver to Order map
     * 
     * @param orderDriverScoreMap
     * @return
     */
    public Map<DriverDTO, OrderDTO>
            getOptimalDriverOrderMapping(Map<OrderDTO, Map<DriverDTO, Double>> orderDriverScoreMap) {
        // Step 1:
        Map<Integer, OrderDTO> orderToIndexMap =
                getIndexMap(orderDriverScoreMap.keySet().stream().collect(Collectors.toSet()));
        Map<Integer, DriverDTO> driverToIndexMap = getIndexMap(orderDriverScoreMap.values().stream().map(Map::keySet)
                .flatMap(Set::stream).distinct().collect(Collectors.toSet()));

        // Step 2:
        double[][] costMatrix = generateCostMatrix(orderDriverScoreMap, driverToIndexMap, orderToIndexMap);

        // Step 3:
        int[] driverJobs = new HungarianAlgorithm(costMatrix).execute();

        // Step 4:
        Map<DriverDTO, OrderDTO> driverToOrderMap = new HashMap<>();
        for (int driverIndex = 0; driverIndex < driverJobs.length; driverIndex++) {
            driverToOrderMap.put(driverToIndexMap.get(driverIndex), orderToIndexMap.get(driverJobs[driverIndex]));
        }

        return driverToOrderMap;
    }

    private double[][] generateCostMatrix(Map<OrderDTO, Map<DriverDTO, Double>> orderDriverScoreMap,
            Map<Integer, DriverDTO> driverToIndexMap, Map<Integer, OrderDTO> orderToIndexMap) {
        double[][] costMatrix = new double[orderToIndexMap.size()][];
        for (int orderIndex = 0; orderIndex < orderToIndexMap.size(); orderIndex++) {
            costMatrix[orderIndex] = new double[driverToIndexMap.size()];
            for (int driverIndex = 0; driverIndex < driverToIndexMap.size(); driverIndex++) {
                if (orderToIndexMap.containsKey(orderIndex) && driverToIndexMap.containsKey(driverIndex)) {
                    if (orderDriverScoreMap.containsKey(orderToIndexMap.get(orderIndex))) {
                        Map<DriverDTO, Double> driverMap = orderDriverScoreMap.get(orderToIndexMap.get(orderIndex));
                        if (driverMap.containsKey(driverToIndexMap.get(driverIndex))) {
                            costMatrix[orderIndex][driverIndex] = driverMap.get(driverToIndexMap.get(driverIndex));
                            continue;
                        }
                    }
                }
                costMatrix[orderIndex][driverIndex] = 0.0;
            }
        }
        return costMatrix;
    }

    private <T> Map<Integer, T> getIndexMap(Set<T> setOfObjects) {
        Map<Integer, T> mapOfIndex = new HashMap<>();
        int i = 0;
        for (T original : setOfObjects) {
            mapOfIndex.put(i++, original);
        }
        return mapOfIndex;
    }

}
