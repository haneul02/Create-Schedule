package com.example.demo.Iayered.repository;

import com.example.demo.Iayered.dto.ScheduleResponseDto;
import com.example.demo.Iayered.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules(String email);
    Optional<Schedule> findScheduleById(Long id);
    int updateSchedule(Long id, String title, String content);
    int updateTitle(Long id, String title);
    int deleteSchedule(Long id);
}
