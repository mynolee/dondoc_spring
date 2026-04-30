package com.example.dondocspring.dto.record;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RecordDto {

    public record CategoryResponse(
            Long id,
            String name,
            String icon,
            String type
    ) {
    }

    public record RecordResponse(
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

    public record MonthlyHistoryResponse(
            Long id,
            Long userId,
            String targetMonth,
            double avgRatio,
            int houseLevel
    ) {
    }
}
