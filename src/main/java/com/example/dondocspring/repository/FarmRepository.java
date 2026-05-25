package com.example.dondocspring.repository;

import com.example.dondocspring.entity.Farm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class FarmRepository {

    private final JdbcTemplate jdbcTemplate;

    public FarmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Farm> findAll() {
        String sql = """
                SELECT id, name, created_at
                FROM farms
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Farm(
                rs.getLong("id"),
                rs.getString("name"),
                toLocalDateTime(rs.getTimestamp("created_at"))
        ));
    }

    public Optional<Farm> findById(Long id) {
        String sql = """
                SELECT id, name, created_at
                FROM farms
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Farm(
                rs.getLong("id"),
                rs.getString("name"),
                toLocalDateTime(rs.getTimestamp("created_at"))
        ), id).stream().findFirst();
    }

    public int save(Farm farm) {
        String sql = """
                INSERT INTO farms (name)
                VALUES (?)
                """;

        return jdbcTemplate.update(sql, farm.name());
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
