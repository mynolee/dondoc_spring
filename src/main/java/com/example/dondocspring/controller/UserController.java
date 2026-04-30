package com.example.dondocspring.controller;

import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.dto.record.RecordDto;
import com.example.dondocspring.dto.user.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    public static final List<UserDto.UserFixture> USER_FIXTURES = List.of(
            new UserDto.UserFixture(
                    new UserDto.UserResponse(1L, "piglet01", "pw1234", "민수", 27, 5, 3, 3200000L, 55,
                            LocalDateTime.of(2026, 4, 1, 9, 0)),
                    List.of(
                            new FarmDto.FarmMemberResponse(1L, 1L, 1L, LocalDateTime.of(2026, 4, 1, 9, 30))
                    ),
                    List.of(
                            new RecordDto.RecordResponse(1L, 1L, 1L, 3200000L, "4월 급여", "월급 입금",
                                    LocalDate.of(2026, 4, 1), LocalDateTime.of(2026, 4, 1, 9, 5)),
                            new RecordDto.RecordResponse(2L, 1L, 2L, 12000L, "점심", "회사 앞 김치찌개",
                                    LocalDate.of(2026, 4, 2), LocalDateTime.of(2026, 4, 2, 12, 10))
                    ),
                    new RecordDto.MonthlyHistoryResponse(1L, 1L, "2026-04", 0.82, 3)
            ),
            new UserDto.UserFixture(
                    new UserDto.UserResponse(2L, "savepig02", "pw5678", "지은", 25, 4, 2, 2800000L, 60,
                            LocalDateTime.of(2026, 4, 3, 10, 30)),
                    List.of(
                            new FarmDto.FarmMemberResponse(2L, 2L, 1L, LocalDateTime.of(2026, 4, 1, 9, 40))
                    ),
                    List.of(
                            new RecordDto.RecordResponse(3L, 2L, 3L, 2500L, "버스", "출근 버스",
                                    LocalDate.of(2026, 4, 2), LocalDateTime.of(2026, 4, 2, 8, 10))
                    ),
                    new RecordDto.MonthlyHistoryResponse(2L, 2L, "2026-04", 0.91, 2)
            ),
            new UserDto.UserFixture(
                    new UserDto.UserResponse(3L, "farmhero03", "pw9999", "도윤", 29, 6, 4, 4100000L, 50,
                            LocalDateTime.of(2026, 4, 5, 14, 15)),
                    List.of(
                            new FarmDto.FarmMemberResponse(3L, 3L, 2L, LocalDateTime.of(2026, 4, 2, 11, 0))
                    ),
                    List.of(
                            new RecordDto.RecordResponse(4L, 3L, 4L, 50000L, "가족 용돈", "생일 축하",
                                    LocalDate.of(2026, 4, 5), LocalDateTime.of(2026, 4, 5, 20, 20))
                    ),
                    new RecordDto.MonthlyHistoryResponse(3L, 3L, "2026-04", 0.76, 4)
            )
    );

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
        return USER_FIXTURES.stream()
                .map(fixture -> new UserDto.UserDetailResponse(
                        fixture.user(),
                        fixture.farmMembers(),
                        fixture.records(),
                        fixture.monthlyHistory()
                ))
                .toList();
    }

    @GetMapping("/users/{id}")
    public UserDto.UserDetailResponse getUser(@PathVariable Long id) {
        UserDto.UserFixture fixture = getUserFixture(id);
        return new UserDto.UserDetailResponse(
                fixture.user(),
                fixture.farmMembers(),
                fixture.records(),
                fixture.monthlyHistory()
        );
    }

    @GetMapping("/users/{id}/records")
    public UserDto.UserRecordsResponse getUserRecords(@PathVariable Long id) {
        UserDto.UserFixture fixture = getUserFixture(id);
        return new UserDto.UserRecordsResponse(fixture.user(), fixture.records());
    }

    private UserDto.UserFixture getUserFixture(Long id) {
        return USER_FIXTURES.stream()
                .filter(userFixture -> userFixture.user().id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }
}
