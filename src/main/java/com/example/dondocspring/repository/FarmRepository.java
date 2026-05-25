package com.example.dondocspring.repository;

import com.example.dondocspring.entity.Farm;
import com.example.dondocspring.entity.FarmMember;
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

    public List<FarmMember> findAllMembers() {
        String sql = """
                SELECT id, user_id, farm_id, joined_at
                FROM farm_members
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmMember(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getObject("farm_id", Long.class),
                toLocalDateTime(rs.getTimestamp("joined_at"))
        ));
    }

    public List<FarmMember> findMembersByFarmId(Long farmId) {
        String sql = """
                SELECT id, user_id, farm_id, joined_at
                FROM farm_members
                WHERE farm_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmMember(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getObject("farm_id", Long.class),
                toLocalDateTime(rs.getTimestamp("joined_at"))
        ), farmId);
    }

    public List<FarmMember> findMembersByUserId(Long userId) {
        String sql = """
                SELECT id, user_id, farm_id, joined_at
                FROM farm_members
                WHERE user_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FarmMember(
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
