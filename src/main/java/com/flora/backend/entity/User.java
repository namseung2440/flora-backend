package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 255)
    private String passwordHash;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(length = 10)
    private String profileEmoji = "🌱";

    @Column(nullable = false, length = 10)
    private String role = "USER";

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Integer points = 0;

    @Column(nullable = false)
    private Integer streakDays = 0;

    private LocalDate lastCheckIn;

    @Column(nullable = false)
    private Boolean locationConsent = false;

    @Column(length = 20)
    private String communityRank = "SEED";

    @Column(nullable = false)
    private Integer communityExp = 0;

    @Column(length = 100)
    private String farmName;

    @Column(length = 500)
    private String sellerDescription;

    @Column(length = 255)
    private String certificationImageUrl;

    @Column(length = 10)
    private String sellerStatus;

    private LocalDateTime approvedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void updateStreak(int days) {
        this.streakDays = days;
        this.lastCheckIn = LocalDate.now();
    }
}
