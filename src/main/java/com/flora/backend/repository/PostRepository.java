package com.flora.backend.repository;

import com.flora.backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByCategoryAndIsActiveTrue(String category, Pageable pageable);
    Page<Post> findByRegionAndIsActiveTrue(String region, Pageable pageable);
}
