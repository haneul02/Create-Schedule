package com.example.demo.Iayered.service;

import com.example.demo.Iayered.dto.ScheduleRequestDto;
import com.example.demo.Iayered.dto.ScheduleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    // 일정 등록
    void createSchedule(ScheduleRequestDto requestDto);

    // 전체 일정 조회
    List<ScheduleResponseDto> getAllSchedules(int page, int size);

    // 단일 일정 조회
    ScheduleResponseDto getScheduleById(Long id);

    // 일정 수정
    ScheduleResponseDto updateSchedule(Long id, String title, String content, String password, String email);

    // 일정 제목만 수정
    ScheduleResponseDto updateTitle(Long id, String title, String password, String email);

    // 일정 삭제
    void deleteSchedule(Long id, String password, String email);

}
