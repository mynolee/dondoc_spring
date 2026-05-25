package com.example.dondocspring.service;

import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.dto.record.RecordDto;
import com.example.dondocspring.dto.user.UserDto;
import com.example.dondocspring.entity.FarmMember;
import com.example.dondocspring.entity.MonthlyHistory;
import com.example.dondocspring.entity.RecordEntity;
import com.example.dondocspring.entity.User;
import com.example.dondocspring.repository.FarmMemberRepository;
import com.example.dondocspring.repository.FarmRepository;
import com.example.dondocspring.repository.MonthlyHistoryRepository;
import com.example.dondocspring.repository.RecordRepository;
import com.example.dondocspring.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FarmMemberRepository farmMemberRepository;
    private final RecordRepository recordRepository;
    private final MonthlyHistoryRepository monthlyHistoryRepository;

    public UserService(
            UserRepository userRepository,
            FarmMemberRepository farmMemberRepository,
            RecordRepository recordRepository,
            MonthlyHistoryRepository monthlyHistoryRepository
    ) {
        this.userRepository = userRepository;
        this.farmMemberRepository = farmMemberRepository;
        this.recordRepository = recordRepository;
        this.monthlyHistoryRepository = monthlyHistoryRepository;
    }

    public List<UserDto.UserDetailResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto.UserDetailResponse(
                        toUserResponse(user),
                        farmMemberRepository.findByUserId(user.id()).stream()
                                .map(this::toFarmMemberResponse)
                                .toList(),
                        recordRepository.findByUserId(user.id()).stream()
                                .map(this::toRecordResponse)
                                .toList(),
                        monthlyHistoryRepository.findByUserId(user.id())
                                .map(this::toMonthlyHistoryResponse)
                                .orElse(null)
                ))
                .toList();
    }

    public UserDto.UserDetailResponse getUser(Long id) {
        User user = findUser(id);
        return new UserDto.UserDetailResponse(
                toUserResponse(user),
                farmMemberRepository.findByUserId(id).stream()
                        .map(this::toFarmMemberResponse)
                        .toList(),
                recordRepository.findByUserId(id).stream()
                        .map(this::toRecordResponse)
                        .toList(),
                monthlyHistoryRepository.findByUserId(id)
                        .map(this::toMonthlyHistoryResponse)
                        .orElse(null)
        );
    }

    public UserDto.UserRecordsResponse getUserRecords(Long id) {
        User user = findUser(id);
        return new UserDto.UserRecordsResponse(
                toUserResponse(user),
                recordRepository.findByUserId(id).stream()
                        .map(this::toRecordResponse)
                        .toList()
        );
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

    private UserDto.UserResponse toUserResponse(User user) {
        return new UserDto.UserResponse(
                user.id(),
                user.userId(),
                user.userPassword(),
                user.name(),
                user.age(),
                user.currentPigLevel(),
                user.currentHouseLevel(),
                user.monthlyIncome(),
                user.targetExpenseRatio(),
                user.createdAt()
        );
    }

    private FarmDto.FarmMemberResponse toFarmMemberResponse(FarmMember farmMember) {
        return new FarmDto.FarmMemberResponse(
                farmMember.id(),
                farmMember.userId(),
                farmMember.farmId(),
                farmMember.joinedAt()
        );
    }

    private RecordDto.RecordResponse toRecordResponse(RecordEntity record) {
        return new RecordDto.RecordResponse(
                record.id(),
                record.userId(),
                record.categoryId(),
                record.amount(),
                record.description(),
                record.memo(),
                record.recordDate(),
                record.createdAt()
        );
    }

    private RecordDto.MonthlyHistoryResponse toMonthlyHistoryResponse(MonthlyHistory monthlyHistory) {
        return new RecordDto.MonthlyHistoryResponse(
                monthlyHistory.id(),
                monthlyHistory.userId(),
                monthlyHistory.targetMonth(),
                monthlyHistory.avgRatio(),
                monthlyHistory.houseLevel()
        );
    }
}
