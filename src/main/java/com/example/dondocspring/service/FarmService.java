package com.example.dondocspring.service;

import com.example.dondocspring.dto.farm.FarmDto;
import com.example.dondocspring.entity.Farm;
import com.example.dondocspring.entity.FarmMember;
import com.example.dondocspring.repository.FarmMemberRepository;
import com.example.dondocspring.repository.FarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FarmService {

    private final FarmRepository farmRepository;
    private final FarmMemberRepository farmMemberRepository;

    public FarmService(FarmRepository farmRepository, FarmMemberRepository farmMemberRepository) {
        this.farmRepository = farmRepository;
        this.farmMemberRepository = farmMemberRepository;
    }

    public List<FarmDto.FarmResponse> getFarms() {
        return farmRepository.findAll().stream()
                .map(this::toFarmResponse)
                .toList();
    }

    public FarmDto.FarmResponse getFarm(Long id) {
        return farmRepository.findById(id)
                .map(this::toFarmResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farm not found"));
    }

    public List<FarmDto.FarmMemberResponse> getFarmMembersByFarm(Long id) {
        getFarm(id);
        return farmMemberRepository.findByFarmId(id).stream()
                .map(this::toFarmMemberResponse)
                .toList();
    }

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
