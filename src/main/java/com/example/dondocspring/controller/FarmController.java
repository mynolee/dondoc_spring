package com.example.dondocspring.controller;

import com.example.dondocspring.dto.farm.FarmDto;
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

    public FarmController(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @GetMapping("/farms")
    public List<FarmDto.FarmResponse> getFarms() {
        return farmRepository.findAll();
    }

    @GetMapping("/farms/{id}")
    public FarmDto.FarmResponse getFarm(@PathVariable Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farm not found"));
    }

    @GetMapping("/farms/{id}/members")
    public List<FarmDto.FarmMemberResponse> getFarmMembersByFarm(@PathVariable Long id) {
        getFarm(id);
        return farmRepository.findMembersByFarmId(id);
    }

    @GetMapping("/farm-members")
    public List<FarmDto.FarmMemberResponse> getFarmMembers() {
        return farmRepository.findAllMembers();
    }
}
