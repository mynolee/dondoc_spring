package com.example.dondocspring.repository;

import com.example.dondocspring.entity.MonthlyHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MonthlyHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public MonthlyHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MonthlyHistory> findAll() {
        String sql = """
                SELECT id, user_id, target_month, avg_ratio, house_level
                FROM monthly_history
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new MonthlyHistory(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getString("target_month"),
                rs.getDouble("avg_ratio"),
                rs.getInt("house_level")
        ));
    }

    public Optional<MonthlyHistory> findByUserId(Long userId) {
        String sql = """
                SELECT id, user_id, target_month, avg_ratio, house_level
                FROM monthly_history
                WHERE user_id = ?
                ORDER BY id
                LIMIT 1
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new MonthlyHistory(
                rs.getLong("id"),
                rs.getObject("user_id", Long.class),
                rs.getString("target_month"),
                rs.getDouble("avg_ratio"),
                rs.getInt("house_level")
        ), userId).stream().findFirst();
    }

    public int save(MonthlyHistory monthlyHistory) {
        String sql = """
                INSERT INTO monthly_history (user_id, target_month, avg_ratio, house_level)
                VALUES (?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                monthlyHistory.userId(),
                monthlyHistory.targetMonth(),
                monthlyHistory.avgRatio(),
                monthlyHistory.houseLevel()
        );
    }
}
