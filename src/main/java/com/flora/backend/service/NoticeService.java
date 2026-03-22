package com.flora.backend.service;

import com.flora.backend.entity.Notice;
import com.flora.backend.exception.ResourceNotFoundException;
import com.flora.backend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getAll() {
        return noticeRepository.findAll();
    }

    public Notice getById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("공지사항을 찾을 수 없습니다."));
    }
}
