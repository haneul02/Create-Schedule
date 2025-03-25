package com.example.demo.Iayered.service;

import com.example.demo.Iayered.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    // 일정 등록
    ScheduleResponseDto createSchedule(String name, String password, String content, String title);

    // 전체 일정 조회
    List<ScheduleResponseDto> getAllSchedules(String email);

    // 단일 일정 조회
    ScheduleResponseDto getScheduleById(Long id);

    // 일정 수정
    ScheduleResponseDto updateSchedule(Long id, String title, String content, String password);

    // 일정 제목만 수정
    ScheduleResponseDto updateTitle(Long id, String title, String password);

    // 일정 삭제
    void deleteSchedule(Long id, String password);
}
