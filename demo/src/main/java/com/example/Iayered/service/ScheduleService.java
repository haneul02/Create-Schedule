package com.example.Iayered.service;

import com.example.Iayered.dto.ScheduleResponseDto;

public interface ScheduleService {

    // 일정 수정
    ScheduleResponseDto updateSchedule(Long id, String title, String content);

    // 일정 제목만 수정
    ScheduleResponseDto updateTitle(Long id, String title, String content);

    // 일정 삭제 
    void deleteSchedule(Long id);
}
