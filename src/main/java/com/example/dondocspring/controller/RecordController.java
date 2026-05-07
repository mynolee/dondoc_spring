package com.example.dondocspring.controller;

import com.example.dondocspring.dto.record.RecordDto;
import com.example.dondocspring.repository.RecordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecordController {

    private final RecordRepository recordRepository;

    public RecordController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    private static final List<RecordDto.CategoryResponse> CATEGORIES = List.of(
            new RecordDto.CategoryResponse(1L, "월급", "salary", "INCOME"),
            new RecordDto.CategoryResponse(2L, "식비", "food", "EXPENSE"),
            new RecordDto.CategoryResponse(3L, "교통", "bus", "EXPENSE"),
            new RecordDto.CategoryResponse(4L, "용돈", "gift", "INCOME")
    );

    @GetMapping("/categories")
    public List<RecordDto.CategoryResponse> getCategories() {
        List<RecordDto.CategoryResponse> dbCategories = recordRepository.findAllCategories();

        return CATEGORIES;
    }

    @GetMapping("/records")
    public List<RecordDto.RecordResponse> getRecords() {
        List<RecordDto.RecordResponse> dbRecords = recordRepository.findAllRecords();

        return UserController.USER_FIXTURES.stream()
                .flatMap(userFixture -> userFixture.records().stream())
                .toList();
    }

    @GetMapping("/monthly-history")
    public List<RecordDto.MonthlyHistoryResponse> getMonthlyHistories() {
        List<RecordDto.MonthlyHistoryResponse> dbMonthlyHistories = recordRepository.findAllMonthlyHistories();

        return UserController.USER_FIXTURES.stream()
                .map(userFixture -> userFixture.monthlyHistory())
                .toList();
    }
}
