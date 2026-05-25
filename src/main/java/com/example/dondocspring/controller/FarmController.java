package com.example.dondocspring.controller;

import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.service.FarmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping("/farms")
    public List<FarmDto.FarmResponse> getFarms() {
        return farmService.getFarms();
    }

    @GetMapping("/farms/{id}")
    public FarmDto.FarmResponse getFarm(@PathVariable Long id) {
        return farmService.getFarm(id);
    }

    @GetMapping("/farms/{id}/members")
    public List<FarmDto.FarmMemberResponse> getFarmMembersByFarm(@PathVariable Long id) {
        return farmService.getFarmMembersByFarm(id);
    }

    @GetMapping("/farm-members")
    public List<FarmDto.FarmMemberResponse> getFarmMembers() {
        return farmService.getFarmMembers();
    }
}
