package com.flora.backend.controller;

import com.flora.backend.entity.Quiz;
import com.flora.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAll() {
        return ResponseEntity.ok(quizService.getAll());
    }

    @GetMapping("/today")
    public ResponseEntity<Quiz> getToday() {
        return ResponseEntity.ok(quizService.getToday());
    }
}
