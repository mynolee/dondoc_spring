package com.example.dondocspring.repository;

import com.example.dondocspring.dto.farm.FarmDto;
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

    public List<FarmDto.FarmResponse> findAll() {
        String sql = """
                SELECT id, name, created_at
                FROM farms
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmDto.FarmResponse(
                rs.getLong("id"),
                rs.getString("name"),
                toLocalDateTime(rs.getTimestamp("created_at"))
        ));
    }

    public Optional<FarmDto.FarmResponse> findById(Long id) {
        String sql = """
                SELECT id, name, created_at
                FROM farms
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmDto.FarmResponse(
                rs.getLong("id"),
                rs.getString("name"),
                toLocalDateTime(rs.getTimestamp("created_at"))
        ), id).stream().findFirst();
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
