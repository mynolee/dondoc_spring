package com.example.dondocspring.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecordEntity(
        Long id,
        Long userId,
        Long categoryId,
        long amount,
        String description,
        String memo,
        LocalDate recordDate,
        LocalDateTime createdAt
) {
}
