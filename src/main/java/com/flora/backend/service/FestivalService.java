package com.flora.backend.service;

import com.flora.backend.entity.Festival;
import com.flora.backend.exception.ResourceNotFoundException;
import com.flora.backend.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public List<Festival> getAll() {
        return festivalRepository.findAll();
    }

    public Festival getById(Long id) {
        return festivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("축제를 찾을 수 없습니다."));
    }
}
