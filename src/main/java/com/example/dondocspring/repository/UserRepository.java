package com.example.dondocspring.repository;

import com.example.dondocspring.dto.user.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserDto.UserResponse> findAll() {
        String sql = """
                SELECT id, user_id, user_password, name, age, current_pig_level,
                       current_house_level, monthly_income, target_expense_ratio, created_at
                FROM users
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserDto.UserResponse(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("user_password"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getInt("current_pig_level"),
                rs.getInt("current_house_level"),
                rs.getLong("monthly_income"),
                rs.getInt("target_expense_ratio"),
                toLocalDateTime(rs.getTimestamp("created_at"))
        ));
    }

    public Optional<UserDto.UserResponse> findById(Long id) {
        String sql = """
                SELECT id, user_id, user_password, name, age, current_pig_level,
                       current_house_level, monthly_income, target_expense_ratio, created_at
                FROM users
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserDto.UserResponse(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("user_password"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getInt("current_pig_level"),
                rs.getInt("current_house_level"),
                rs.getLong("monthly_income"),
                rs.getInt("target_expense_ratio"),
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
