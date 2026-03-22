package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plant_calendars")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PlantCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 24)
    private String plantId;

    @Column(nullable = false, length = 100)
    private String plantNickname;

    private Integer wateringCycleDays;
    private LocalDate wateringNextDate;
    private Boolean wateringIsDone = false;

    private LocalDate repotDate;
    private Boolean repotIsDone = false;

    private LocalDate fertilizeDate;
    private Boolean fertilizeIsDone = false;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GrowthDiary> growthDiaries = new ArrayList<>();

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CareLog> careLogs = new ArrayList<>();

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void completeWatering() {
        this.wateringIsDone = true;
        this.wateringNextDate = LocalDate.now().plusDays(wateringCycleDays);
    }
}
