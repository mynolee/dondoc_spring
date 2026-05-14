package com.example.dondocspring.entity;

import java.time.LocalDateTime;

public record Farm(
        Long id,
        String name,
        LocalDateTime createdAt
) {
}
