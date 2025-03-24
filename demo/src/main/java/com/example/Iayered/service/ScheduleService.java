package com.example.Iayered.service;

import com.example.Iayered.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    // 일정 등록
    ScheduleResponseDto createSchedule(String name, String password, String content, String title);

    // 전체 일정 조회
    List<ScheduleResponseDto> getAllSchedules();

    // 단일 일정 조회
    ScheduleResponseDto getScheduleById(Long id);

    // 일정 수정
    ScheduleResponseDto updateSchedule(Long id, String title, String content);

    // 일정 제목만 수정
    ScheduleResponseDto updateTitle(Long id, String title, String content);

    // 일정 삭제
    void deleteSchedule(Long id);
}
