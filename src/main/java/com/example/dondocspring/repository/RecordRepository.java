package com.example.dondocspring.repository;

import com.example.dondocspring.dto.record.RecordDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RecordDto.RecordResponse> findAll() {
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

    public List<RecordDto.RecordResponse> findByUserId(Long userId) {
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

    public int save(RecordDto.RecordResponse record) {
        String sql = """
                INSERT INTO records (
                    user_id, category_id, amount, description, memo, record_date
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                record.userId(),
                record.categoryId(),
                record.amount(),
                record.description(),
                record.memo(),
                record.recordDate()
        );
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
