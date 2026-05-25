package com.example.dondocspring.entity;

public record MonthlyHistory(
        Long id,
        Long userId,
        String targetMonth,
        double avgRatio,
        int houseLevel
) {
}
