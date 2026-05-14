package com.example.dondocspring.repository;

import com.example.dondocspring.dto.record.RecordDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RecordDto.CategoryResponse> findAll() {
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
}
