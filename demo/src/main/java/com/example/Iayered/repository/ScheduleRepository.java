package com.example.Iayered.repository;

import com.example.Iayered.dto.ScheduleResponseDto;
import com.example.Iayered.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    Optional<Schedule> findScheduleById(Long id);
    int updateSchedule(Long id, String tiltle, String content);
    int updateTitle(Long id, String title);
    int deleteSchedule(Long id);
}
