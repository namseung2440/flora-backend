package com.flora.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(length = 24)
    private String plantId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer price;

    private Integer originalPrice;

    @Column(nullable = false)
    private Integer stockQuantity = 0;

    @Column(length = 20)
    private String category;

    @Column(length = 20)
    private String badgeType;

    @Column(length = 20)
    private String badgeLabel;

    @Column(length = 20)
    private String productType = "NORMAL";

    private Boolean isEcoCertified = false;
    private Boolean isGroupBuy = false;
    private Integer groupBuyMinQty;
    private Integer groupBuyCurrentQty = 0;

    @Column
    private Double rating = 0.0;

    private Integer reviewCount = 0;
    private Boolean isActive = true;

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

    public void update(String name, String description, Integer price,
                       Integer stockQuantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public void decreaseStock(int quantity) {
        this.stockQuantity -= quantity;
    }
}
