package com.example.dondocspring.controller;

import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.entity.Farm;
import com.example.dondocspring.entity.FarmMember;
import com.example.dondocspring.repository.FarmMemberRepository;
import com.example.dondocspring.repository.FarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FarmController {

    private final FarmRepository farmRepository;
    private final FarmMemberRepository farmMemberRepository;

    public FarmController(FarmRepository farmRepository, FarmMemberRepository farmMemberRepository) {
        this.farmRepository = farmRepository;
        this.farmMemberRepository = farmMemberRepository;
    }

    @GetMapping("/farms")
    public List<FarmDto.FarmResponse> getFarms() {
        return farmRepository.findAll().stream()
                .map(this::toFarmResponse)
                .toList();
    }

    @GetMapping("/farms/{id}")
    public FarmDto.FarmResponse getFarm(@PathVariable Long id) {
        return farmRepository.findById(id)
                .map(this::toFarmResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farm not found"));
    }

    @GetMapping("/farms/{id}/members")
    public List<FarmDto.FarmMemberResponse> getFarmMembersByFarm(@PathVariable Long id) {
        getFarm(id);
        return farmMemberRepository.findByFarmId(id).stream()
                .map(this::toFarmMemberResponse)
                .toList();
    }

    @GetMapping("/farm-members")
    public List<FarmDto.FarmMemberResponse> getFarmMembers() {
        return farmMemberRepository.findAll().stream()
                .map(this::toFarmMemberResponse)
                .toList();
    }

    private FarmDto.FarmResponse toFarmResponse(Farm farm) {
        return new FarmDto.FarmResponse(farm.id(), farm.name(), farm.createdAt());
    }

    private FarmDto.FarmMemberResponse toFarmMemberResponse(FarmMember farmMember) {
        return new FarmDto.FarmMemberResponse(
                farmMember.id(),
                farmMember.userId(),
                farmMember.farmId(),
                farmMember.joinedAt()
        );
    }
}
