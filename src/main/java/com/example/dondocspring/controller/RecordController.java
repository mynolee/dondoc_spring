package com.example.dondocspring.controller;

import com.example.dondocspring.dto.record.RecordDto;
import com.example.dondocspring.entity.Category;
import com.example.dondocspring.entity.MonthlyHistory;
import com.example.dondocspring.entity.RecordEntity;
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
        return categoryRepository.findAll().stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    @GetMapping("/records")
    public List<RecordDto.RecordResponse> getRecords() {
        return recordRepository.findAll().stream()
                .map(this::toRecordResponse)
                .toList();
    }

    @GetMapping("/monthly-history")
    public List<RecordDto.MonthlyHistoryResponse> getMonthlyHistories() {
        return monthlyHistoryRepository.findAll().stream()
                .map(this::toMonthlyHistoryResponse)
                .toList();
    }

    private RecordDto.CategoryResponse toCategoryResponse(Category category) {
        return new RecordDto.CategoryResponse(
                category.id(),
                category.name(),
                category.icon(),
                category.type()
        );
    }

    private RecordDto.RecordResponse toRecordResponse(RecordEntity record) {
        return new RecordDto.RecordResponse(
                record.id(),
                record.userId(),
                record.categoryId(),
                record.amount(),
                record.description(),
                record.memo(),
                record.recordDate(),
                record.createdAt()
        );
    }

    private RecordDto.MonthlyHistoryResponse toMonthlyHistoryResponse(MonthlyHistory monthlyHistory) {
        return new RecordDto.MonthlyHistoryResponse(
                monthlyHistory.id(),
                monthlyHistory.userId(),
                monthlyHistory.targetMonth(),
                monthlyHistory.avgRatio(),
                monthlyHistory.houseLevel()
        );
    }
}
