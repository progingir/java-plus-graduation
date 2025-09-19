package ru.practicum.service;

import java.util.HashMap;
import java.util.Map;

public class MinWeightsMatrix {

    private final Map<Long, Map<Long, Double>> minWeightsSums = new HashMap<>();

    public void put(long eventA, long eventB, double sum) {
        long first  = Math.min(eventA, eventB);
        long second = Math.max(eventA, eventB);
        minWeightsSums.computeIfAbsent(first, k -> new HashMap<>()).put(second, sum);
    }

    public double get(long eventA, long eventB) {
        long first  = Math.min(eventA, eventB);
        long second = Math.max(eventA, eventB);
        return minWeightsSums.getOrDefault(first, Map.of())
                .getOrDefault(second, 0.0);
    }
}