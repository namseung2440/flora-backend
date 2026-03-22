package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "care_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CareLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private PlantCalendar calendar;

    @Column(length = 20)
    private String careType;

    private LocalDateTime completedAt;
    private Integer pointsEarned = 0;

    @Column(length = 200)
    private String memo;

    @PrePersist
    protected void onCreate() {
        completedAt = LocalDateTime.now();
    }
}
