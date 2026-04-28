package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class DummyApiController {

	private static final List<FarmResponse> FARMS = List.of(
		new FarmResponse(1L, "절약 농장", LocalDateTime.of(2026, 4, 1, 8, 0)),
		new FarmResponse(2L, "저축 마을", LocalDateTime.of(2026, 4, 2, 8, 30))
	);

	private static final List<CategoryResponse> CATEGORIES = List.of(
		new CategoryResponse(1L, "월급", "salary", "INCOME"),
		new CategoryResponse(2L, "식비", "food", "EXPENSE"),
		new CategoryResponse(3L, "교통", "bus", "EXPENSE"),
		new CategoryResponse(4L, "용돈", "gift", "INCOME")
	);

	private static final List<UserFixture> USER_FIXTURES = List.of(
		new UserFixture(
			new UserResponse(1L, "piglet01", "pw1234", "민수", 27, 5, 3, 3200000L, 55,
				LocalDateTime.of(2026, 4, 1, 9, 0)),
			List.of(
				new FarmMemberResponse(1L, 1L, 1L, LocalDateTime.of(2026, 4, 1, 9, 30))
			),
			List.of(
				new RecordResponse(1L, 1L, 1L, 3200000L, "4월 급여", "월급 입금", LocalDate.of(2026, 4, 1),
					LocalDateTime.of(2026, 4, 1, 9, 5)),
				new RecordResponse(2L, 1L, 2L, 12000L, "점심", "회사 앞 김치찌개", LocalDate.of(2026, 4, 2),
					LocalDateTime.of(2026, 4, 2, 12, 10))
			),
			new MonthlyHistoryResponse(1L, 1L, "2026-04", 0.82, 3)
		),
		new UserFixture(
			new UserResponse(2L, "savepig02", "pw5678", "지은", 25, 4, 2, 2800000L, 60,
				LocalDateTime.of(2026, 4, 3, 10, 30)),
			List.of(
				new FarmMemberResponse(2L, 2L, 1L, LocalDateTime.of(2026, 4, 1, 9, 40))
			),
			List.of(
				new RecordResponse(3L, 2L, 3L, 2500L, "버스", "출근 버스", LocalDate.of(2026, 4, 2),
					LocalDateTime.of(2026, 4, 2, 8, 10))
			),
			new MonthlyHistoryResponse(2L, 2L, "2026-04", 0.91, 2)
		),
		new UserFixture(
			new UserResponse(3L, "farmhero03", "pw9999", "도윤", 29, 6, 4, 4100000L, 50,
				LocalDateTime.of(2026, 4, 5, 14, 15)),
			List.of(
				new FarmMemberResponse(3L, 3L, 2L, LocalDateTime.of(2026, 4, 2, 11, 0))
			),
			List.of(
				new RecordResponse(4L, 3L, 4L, 50000L, "가족 용돈", "생일 축하", LocalDate.of(2026, 4, 5),
					LocalDateTime.of(2026, 4, 5, 20, 20))
			),
			new MonthlyHistoryResponse(3L, 3L, "2026-04", 0.76, 4)
		)
	);

	@GetMapping
	public Map<String, Object> apiHome() {
		return Map.of(
			"message", "dummy api is running",
			"endpoints", List.of(
				"/api/users",
				"/api/users/{id}",
				"/api/users/{id}/records",
				"/api/farms",
				"/api/farms/{id}",
				"/api/farms/{id}/members",
				"/api/farm-members",
				"/api/categories",
				"/api/records",
				"/api/monthly-history"
			)
		);
	}

	@GetMapping("/users")
	public List<UserDetailResponse> getUsers() {
		return USER_FIXTURES.stream()
			.map(fixture -> new UserDetailResponse(
				fixture.user(),
				fixture.farmMembers(),
				fixture.records(),
				fixture.monthlyHistory()
			))
			.toList();
	}

	@GetMapping("/users/{id}")
	public UserDetailResponse getUser(@PathVariable Long id) {
		UserFixture fixture = getUserFixture(id);
		return new UserDetailResponse(
			fixture.user(),
			fixture.farmMembers(),
			fixture.records(),
			fixture.monthlyHistory()
		);
	}

	@GetMapping("/users/{id}/records")
	public UserRecordsResponse getUserRecords(@PathVariable Long id) {
		UserFixture fixture = getUserFixture(id);
		return new UserRecordsResponse(fixture.user(), fixture.records());
	}

	@GetMapping("/farms")
	public List<FarmResponse> getFarms() {
		return FARMS;
	}

	@GetMapping("/farms/{id}")
	public FarmResponse getFarm(@PathVariable Long id) {
		return FARMS.stream()
			.filter(farm -> farm.id().equals(id))
			.findFirst()
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farm not found"));
	}

	@GetMapping("/farms/{id}/members")
	public List<FarmMemberResponse> getFarmMembersByFarm(@PathVariable Long id) {
		getFarm(id);
		return USER_FIXTURES.stream()
			.flatMap(userFixture -> userFixture.farmMembers().stream())
			.filter(member -> member.farmId().equals(id))
			.toList();
	}

	@GetMapping("/farm-members")
	public List<FarmMemberResponse> getFarmMembers() {
		return USER_FIXTURES.stream()
			.flatMap(userFixture -> userFixture.farmMembers().stream())
			.toList();
	}

	@GetMapping("/categories")
	public List<CategoryResponse> getCategories() {
		return CATEGORIES;
	}

	@GetMapping("/records")
	public List<RecordResponse> getRecords() {
		return USER_FIXTURES.stream()
			.flatMap(userFixture -> userFixture.records().stream())
			.toList();
	}

	@GetMapping("/monthly-history")
	public List<MonthlyHistoryResponse> getMonthlyHistories() {
		return USER_FIXTURES.stream()
			.map(UserFixture::monthlyHistory)
			.toList();
	}

	private UserFixture getUserFixture(Long id) {
		return USER_FIXTURES.stream()
			.filter(userFixture -> userFixture.user().id().equals(id))
			.findFirst()
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
	}

	private record UserFixture(
		UserResponse user,
		List<FarmMemberResponse> farmMembers,
		List<RecordResponse> records,
		MonthlyHistoryResponse monthlyHistory
	) {
	}

	private record UserResponse(
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

	private record FarmResponse(
		Long id,
		String name,
		LocalDateTime createdAt
	) {
	}

	private record FarmMemberResponse(
		Long id,
		Long userId,
		Long farmId,
		LocalDateTime joinedAt
	) {
	}

	private record CategoryResponse(
		Long id,
		String name,
		String icon,
		String type
	) {
	}

	private record RecordResponse(
		Long id,
		Long userId,
		Long categoryId,
		long amount,
		String description,
		String memo,
		LocalDate recordDate,
		LocalDateTime createdAt
	) {
	}

	private record UserRecordsResponse(
		UserResponse user,
		List<RecordResponse> records
	) {
	}

	private record UserDetailResponse(
		UserResponse user,
		List<FarmMemberResponse> farmMembers,
		List<RecordResponse> records,
		MonthlyHistoryResponse monthlyHistory
	) {
	}

	private record MonthlyHistoryResponse(
		Long id,
		Long userId,
		String targetMonth,
		double avgRatio,
		int houseLevel
	) {
	}
}
