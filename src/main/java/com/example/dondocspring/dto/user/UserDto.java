package com.example.dondocspring.dto.user;

import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.dto.record.RecordDto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    public record UserFixture(
            UserResponse user,
            List<FarmDto.FarmMemberResponse> farmMembers,
            List<RecordDto.RecordResponse> records,
            RecordDto.MonthlyHistoryResponse monthlyHistory
    ) {
    }

    public record UserResponse(
            Long id,
            String userId,
            String userPassword,
            String name,
            int age,
            int currentPigLevel,
            int currentHouseLevel,
            long monthlyIncome,
            int targetExpenseRatio,
            LocalDateTime createdAt
    ) {
    }

    public record UserDetailResponse(
            UserResponse user,
            List<FarmDto.FarmMemberResponse> farmMembers,
            List<RecordDto.RecordResponse> records,
            RecordDto.MonthlyHistoryResponse monthlyHistory
    ) {
    }

    public record UserRecordsResponse(
            UserResponse user,
            List<RecordDto.RecordResponse> records
    ) {
    }
}
