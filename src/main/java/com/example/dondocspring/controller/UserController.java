package com.example.dondocspring.controller;

import com.example.dondocspring.dto.user.UserDto;
import com.example.dondocspring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public UserDto.UserDetailResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/users/{id}/records")
    public UserDto.UserRecordsResponse getUserRecords(@PathVariable Long id) {
        return userService.getUserRecords(id);
    }
}
