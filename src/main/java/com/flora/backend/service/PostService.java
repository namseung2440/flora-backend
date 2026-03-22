package com.flora.backend.service;

import com.flora.backend.entity.Post;
import com.flora.backend.exception.ResourceNotFoundException;
import com.flora.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public List<Post> getByCategory(String category) {
        return postRepository.findByCategory(category);
    }

    public Post getById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }
}