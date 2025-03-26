package com.example.demo.Iayered.repository;

import com.example.demo.Iayered.dto.ScheduleRequestDto;
import com.example.demo.Iayered.dto.ScheduleResponseDto;
import com.example.demo.Iayered.dto.UserDto;
import com.example.demo.Iayered.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    void saveSchedule(ScheduleRequestDto requestDto);
    List<ScheduleResponseDto> findAllSchedules(int page, int size);
    Optional<Schedule> findScheduleById(Long id);
    int updateSchedule(Long id, String title, String content);
    int updateTitle(Long id, String title);
    int deleteSchedule(Long id);
    UserDto findEmailAndPassword(Long id);
}
