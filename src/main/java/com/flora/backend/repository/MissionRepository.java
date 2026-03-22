package com.flora.backend.repository;

import com.flora.backend.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByIsActiveTrue();
    List<Mission> findByMissionTypeAndIsActiveTrue(String missionType);
}
