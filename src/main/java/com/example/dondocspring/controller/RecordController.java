package com.example.dondocspring.controller;

import com.example.dondocspring.dto.record.RecordDto;
import com.example.dondocspring.repository.CategoryRepository;
import com.example.dondocspring.repository.MonthlyHistoryRepository;
import com.example.dondocspring.repository.RecordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecordController {

    private final RecordRepository recordRepository;
    private final CategoryRepository categoryRepository;
    private final MonthlyHistoryRepository monthlyHistoryRepository;

    public RecordController(
            RecordRepository recordRepository,
            CategoryRepository categoryRepository,
            MonthlyHistoryRepository monthlyHistoryRepository
    ) {
        this.recordRepository = recordRepository;
        this.categoryRepository = categoryRepository;
        this.monthlyHistoryRepository = monthlyHistoryRepository;
    }

    @GetMapping("/categories")
    public List<RecordDto.CategoryResponse> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/records")
    public List<RecordDto.RecordResponse> getRecords() {
        return recordRepository.findAll();
    }

    @GetMapping("/monthly-history")
    public List<RecordDto.MonthlyHistoryResponse> getMonthlyHistories() {
        return monthlyHistoryRepository.findAll();
    }
}
