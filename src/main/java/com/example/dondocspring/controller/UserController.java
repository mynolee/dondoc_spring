package com.example.dondocspring.controller;

import com.example.dondocspring.dto.user.UserDto;
import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.dto.record.RecordDto;
import com.example.dondocspring.entity.FarmMember;
import com.example.dondocspring.entity.MonthlyHistory;
import com.example.dondocspring.entity.RecordEntity;
import com.example.dondocspring.entity.User;
import com.example.dondocspring.repository.FarmMemberRepository;
import com.example.dondocspring.repository.MonthlyHistoryRepository;
import com.example.dondocspring.repository.RecordRepository;
import com.example.dondocspring.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final FarmMemberRepository farmMemberRepository;
    private final RecordRepository recordRepository;
    private final MonthlyHistoryRepository monthlyHistoryRepository;

    public UserController(
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

    @GetMapping("/users/home")
    public Map<String, Object> usersHome() {
        return Map.of("message", "useruser", "endpoints",
                List.of(
                        "/api/users",
                        "/api/users/{id}",
                        "/api/users/{id}/records"
                )
        );
    }

    @GetMapping("/users")
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

    @GetMapping("/users/{id}")
    public UserDto.UserDetailResponse getUser(@PathVariable Long id) {
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

    @GetMapping("/users/{id}/records")
    public UserDto.UserRecordsResponse getUserRecords(@PathVariable Long id) {
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
