package com.flora.backend.controller;

import com.flora.backend.entity.Festival;
import com.flora.backend.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FestivalController {

    private final FestivalService festivalService;

    @GetMapping
    public ResponseEntity<List<Festival>> getAll() {
        return ResponseEntity.ok(festivalService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Festival> getById(@PathVariable Long id) {
        return ResponseEntity.ok(festivalService.getById(id));
    }
}
