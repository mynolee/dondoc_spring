package com.example.dondocspring.entity;

import java.time.LocalDateTime;

public record FarmMember(
        Long id,
        Long userId,
        Long farmId,
        LocalDateTime joinedAt
) {
}
