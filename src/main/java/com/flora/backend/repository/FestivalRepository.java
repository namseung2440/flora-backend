package com.flora.backend.repository;

import com.flora.backend.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
    List<Festival> findByEndDateGreaterThanEqualOrderByStartDate(LocalDate date);
}
