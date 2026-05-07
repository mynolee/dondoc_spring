package com.example.dondocspring.repository;

import com.example.dondocspring.dto.record.RecordDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RecordDto.CategoryResponse> findAllCategories() {
        String sql = """
                SELECT id, name, icon, type
                FROM categories
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RecordDto.CategoryResponse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("icon"),
                rs.getString("type")
        ));
    }

    public List<RecordDto.RecordResponse> findAllRecords() {
        String sql = """
                SELECT id, user_id, category_id, amount, description, memo, record_date, created_at
                FROM records
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RecordDto.RecordResponse(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getObject("category_id", Long.class),
                rs.getLong("amount"),
                rs.getString("description"),
                rs.getString("memo"),
                toLocalDate(rs.getDate("record_date")),
                toLocalDateTime(rs.getTimestamp("created_at"))
        ));
    }

    public List<RecordDto.RecordResponse> findRecordsByUserId(Long userId) {
        String sql = """
                SELECT id, user_id, category_id, amount, description, memo, record_date, created_at
                FROM records
                WHERE user_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RecordDto.RecordResponse(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getObject("category_id", Long.class),
                rs.getLong("amount"),
                rs.getString("description"),
                rs.getString("memo"),
                toLocalDate(rs.getDate("record_date")),
                toLocalDateTime(rs.getTimestamp("created_at"))
        ), userId);
    }

    public List<RecordDto.MonthlyHistoryResponse> findAllMonthlyHistories() {
        String sql = """
                SELECT id, user_id, target_month, avg_ratio, house_level
                FROM monthly_history
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RecordDto.MonthlyHistoryResponse(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getString("target_month"),
                rs.getDouble("avg_ratio"),
                rs.getInt("house_level")
        ));
    }

    public Optional<RecordDto.MonthlyHistoryResponse> findMonthlyHistoryByUserId(Long userId) {
        String sql = """
                SELECT id, user_id, target_month, avg_ratio, house_level
                FROM monthly_history
                WHERE user_id = ?
                ORDER BY id
                LIMIT 1
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RecordDto.MonthlyHistoryResponse(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getString("target_month"),
                rs.getDouble("avg_ratio"),
                rs.getInt("house_level")
        ), userId).stream().findFirst();
    }

    private LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
