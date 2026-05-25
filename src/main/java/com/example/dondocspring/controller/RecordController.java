package com.example.dondocspring.controller;

import com.example.dondocspring.dto.record.RecordDto;
import com.example.dondocspring.service.RecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/categories")
    public List<RecordDto.CategoryResponse> getCategories() {
        return recordService.getCategories();
    }

    @GetMapping("/records")
    public List<RecordDto.RecordResponse> getRecords() {
        return recordService.getRecords();
    }

    @GetMapping("/monthly-history")
    public List<RecordDto.MonthlyHistoryResponse> getMonthlyHistories() {
        return recordService.getMonthlyHistories();
    }
}
