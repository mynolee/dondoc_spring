package com.example.dondocspring.repository;

import com.example.dondocspring.entity.FarmMember;
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

    public List<FarmMember> findAll() {
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

    public List<FarmMember> findByFarmId(Long farmId) {
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

    public List<FarmMember> findByUserId(Long userId) {
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

    public int save(FarmMember farmMember) {
        String sql = """
                INSERT INTO farm_members (user_id, farm_id)
                VALUES (?, ?)
                """;

        return jdbcTemplate.update(sql, farmMember.userId(), farmMember.farmId());
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
