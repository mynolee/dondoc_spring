package com.example.dondocspring.repository;

import com.example.dondocspring.dto.farm.FarmDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FarmMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public FarmMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FarmDto.FarmMemberResponse> findAll() {
        String sql = """
                SELECT id, user_id, farm_id, joined_at
                FROM farm_members
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmDto.FarmMemberResponse(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getObject("farm_id", Long.class),
                toLocalDateTime(rs.getTimestamp("joined_at"))
        ));
    }

    public List<FarmDto.FarmMemberResponse> findByFarmId(Long farmId) {
        String sql = """
                SELECT id, user_id, farm_id, joined_at
                FROM farm_members
                WHERE farm_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmDto.FarmMemberResponse(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getObject("farm_id", Long.class),
                toLocalDateTime(rs.getTimestamp("joined_at"))
        ), farmId);
    }

    public List<FarmDto.FarmMemberResponse> findByUserId(Long userId) {
        String sql = """
                SELECT id, user_id, farm_id, joined_at
                FROM farm_members
                WHERE user_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmDto.FarmMemberResponse(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getObject("farm_id", Long.class),
                toLocalDateTime(rs.getTimestamp("joined_at"))
        ), userId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
