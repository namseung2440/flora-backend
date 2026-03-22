package com.flora.backend.service;

import com.flora.backend.entity.Quiz;
import com.flora.backend.exception.ResourceNotFoundException;
import com.flora.backend.repository.MissionRepository;
import com.flora.backend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.flora.backend.repository.QuizRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    // 날짜 기반으로 오늘의 퀴즈 1개 반환
    public Quiz getToday() {
        List<Quiz> quizzes = quizRepository.findAll();
        if (quizzes.isEmpty()) throw new ResourceNotFoundException("퀴즈가 없습니다.");
        int idx = LocalDate.now().getDayOfYear() % quizzes.size();
        return quizzes.get(idx);
    }
}
