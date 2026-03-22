package com.flora.backend.repository;

import com.flora.backend.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Page<Plant> findByNameContaining(String keyword, Pageable pageable);
    Page<Plant> findByDifficulty(String difficulty, Pageable pageable);
}
