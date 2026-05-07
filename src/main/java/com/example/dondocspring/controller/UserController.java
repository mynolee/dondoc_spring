package com.example.dondocspring.controller;

import com.example.dondocspring.dto.user.UserDto;
import com.example.dondocspring.repository.FarmRepository;
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
    private final FarmRepository farmRepository;
    private final RecordRepository recordRepository;

    public UserController(
            UserRepository userRepository,
            FarmRepository farmRepository,
            RecordRepository recordRepository
    ) {
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.recordRepository = recordRepository;
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
                        user,
                        farmRepository.findMembersByUserId(user.id()),
                        recordRepository.findRecordsByUserId(user.id()),
                        recordRepository.findMonthlyHistoryByUserId(user.id()).orElse(null)
                ))
                .toList();
    }

    @GetMapping("/users/{id}")
    public UserDto.UserDetailResponse getUser(@PathVariable Long id) {
        UserDto.UserResponse user = findUser(id);
        return new UserDto.UserDetailResponse(
                user,
                farmRepository.findMembersByUserId(id),
                recordRepository.findRecordsByUserId(id),
                recordRepository.findMonthlyHistoryByUserId(id).orElse(null)
        );
    }

    @GetMapping("/users/{id}/records")
    public UserDto.UserRecordsResponse getUserRecords(@PathVariable Long id) {
        UserDto.UserResponse user = findUser(id);
        return new UserDto.UserRecordsResponse(user, recordRepository.findRecordsByUserId(id));
    }

    private UserDto.UserResponse findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }
}
