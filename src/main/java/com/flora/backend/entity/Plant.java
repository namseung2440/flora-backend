package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String scientificName;

    @Column(length = 5)
    private String birthFlowerDate;

    @Column(length = 200)
    private String flowerLanguage;

    private Boolean isToxicToPets = false;

    @Column(length = 10)
    private String difficulty;

    @Column(length = 50)
    private String light;

    @Column(length = 200)
    private String wateringTip;

    private Integer temperatureMin;
    private Integer temperatureMax;

    @Column(length = 50)
    private String humidity;

    @Column(length = 100)
    private String soil;

    @Column(length = 200)
    private String tags;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
