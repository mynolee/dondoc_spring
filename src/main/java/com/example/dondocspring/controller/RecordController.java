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

    @GetMapping("/categories")
    public List<RecordDto.CategoryResponse> getCategories() {
        return recordRepository.findAllCategories();
    }

    @GetMapping("/records")
    public List<RecordDto.RecordResponse> getRecords() {
        return recordRepository.findAllRecords();
    }

    @GetMapping("/monthly-history")
    public List<RecordDto.MonthlyHistoryResponse> getMonthlyHistories() {
        return recordRepository.findAllMonthlyHistories();
    }
}
