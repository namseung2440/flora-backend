package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscriptions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 10)
    private String plan;

    @Column(length = 10)
    private String status = "ACTIVE";

    private LocalDate nextDeliveryDate;

    @Column(nullable = false, length = 200)
    private String deliveryAddress;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    @Builder.Default
    private List<AnniversaryDelivery> anniversaryDeliveries = new ArrayList<>();

    private LocalDateTime subscribedAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        subscribedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = "CANCELLED";
    }

    public void pause() {
        this.status = "PAUSED";
    }
}
