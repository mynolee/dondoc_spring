package com.example.dondocspring.repository;

import com.example.dondocspring.entity.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll() {
        String sql = """
                SELECT id, name, icon, type
                FROM categories
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Category(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("icon"),
                rs.getString("type")
        ));
    }

    public int save(Category category) {
        String sql = """
                INSERT INTO categories (name, icon, type)
                VALUES (?, ?, ?)
                """;

        return jdbcTemplate.update(sql, category.name(), category.icon(), category.type());
    }
}
