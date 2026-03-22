package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "missions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 300)
    private String description;

    @Column(length = 20)
    private String missionType;

    @Column(nullable = false, length = 30)
    private String triggerAction;

    private Integer rewardPoints = 0;
    private Boolean isActive = true;
}
