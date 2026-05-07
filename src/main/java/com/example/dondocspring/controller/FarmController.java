package com.example.dondocspring.controller;

import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.repository.FarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FarmController {

    private final FarmRepository farmRepository;

    public FarmController(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    private static final List<FarmDto.FarmResponse> FARMS = List.of(
            new FarmDto.FarmResponse(1L, "절약 농장", LocalDateTime.of(2026, 4, 1, 8, 0)),
            new FarmDto.FarmResponse(2L, "저축 마을", LocalDateTime.of(2026, 4, 2, 8, 30))
    );

    @GetMapping("/farms")
    public List<FarmDto.FarmResponse> getFarms() {
        List<FarmDto.FarmResponse> dbFarms = farmRepository.findAll();

        return FARMS;
    }

    @GetMapping("/farms/{id}")
    public FarmDto.FarmResponse getFarm(@PathVariable Long id) {
        FarmDto.FarmResponse dbFarm = farmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farm not found"));

        return FARMS.stream()
                .filter(farm -> farm.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farm not found"));
    }

    @GetMapping("/farms/{id}/members")
    public List<FarmDto.FarmMemberResponse> getFarmMembersByFarm(@PathVariable Long id) {
        List<FarmDto.FarmMemberResponse> dbFarmMembers = farmRepository.findMembersByFarmId(id);

        getFarm(id);
        return getFarmMembers().stream()
                .filter(member -> member.farmId().equals(id))
                .toList();
    }

    @GetMapping("/farm-members")
    public List<FarmDto.FarmMemberResponse> getFarmMembers() {
        List<FarmDto.FarmMemberResponse> dbFarmMembers = farmRepository.findAllMembers();

        return UserController.USER_FIXTURES.stream()
                .flatMap(userFixture -> userFixture.farmMembers().stream())
                .toList();
    }
}
