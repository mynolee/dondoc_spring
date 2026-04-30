package com.example.dondocspring.dto.farm;

import java.time.LocalDateTime;

public class FarmDto {

    public record FarmResponse(
            Long id,
            String name,
            LocalDateTime createdAt
    ) {
    }

    public record FarmMemberResponse(
            Long id,
            Long userId,
            Long farmId,
            LocalDateTime joinedAt
    ) {
    }
}
