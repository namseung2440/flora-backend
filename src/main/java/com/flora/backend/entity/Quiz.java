package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String question;

    @Column(name = "option_1", length = 100)
    private String option1;

    @Column(name = "option_2", length = 100)
    private String option2;

    @Column(name = "option_3", length = 100)
    private String option3;

    @Column(name = "option_4", length = 100)
    private String option4;

    @Column(name = "answer_idx", nullable = false)
    private Integer answerIdx;

    @Column(length = 500)
    private String explanation;

    @Column(name = "reward_points")
    private Integer rewardPoints = 10;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}