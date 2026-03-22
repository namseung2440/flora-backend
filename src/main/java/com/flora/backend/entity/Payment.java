package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(length = 100)
    private String pgTransactionId;

    @Column(nullable = false)
    private Integer amount;

    @Column(length = 15)
    private String method;

    @Column(length = 10)
    private String status = "PENDING";

    private LocalDateTime paidAt;

    @PrePersist
    protected void onCreate() {
        paidAt = LocalDateTime.now();
    }

    public void complete(String pgTransactionId) {
        this.pgTransactionId = pgTransactionId;
        this.status = "SUCCESS";
        this.paidAt = LocalDateTime.now();
    }
}
