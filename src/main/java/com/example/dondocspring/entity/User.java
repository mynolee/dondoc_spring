package com.example.dondocspring.entity;

import java.time.LocalDateTime;

public record User(
        Long id,
        String userId,
        String userPassword,
        String name,
        int age,
        int currentPigLevel,
        int currentHouseLevel,
        long monthlyIncome,
        int targetExpenseRatio,
        LocalDateTime createdAt
) {
}
