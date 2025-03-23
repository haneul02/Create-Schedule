package com.example.Iayered.controller;

import com.example.Iayered.dto.ScheduleResponseDto;
import com.example.Iayered.entity.Schedule;
import com.example.Iayered.service.ScheduleService;
import com.example.Iayered.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponseDto createSchedule(@RequestBody Schedule schedule) {
        return scheduleRepository.saveSchedule(schedule);
    }

    // 전체 일정 조회
    @GetMapping
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    // 단일 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return new ScheduleResponseDto(schedule);
    }

    // 일정 전체 수정 (제목 + 내용)
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id,
                                              @RequestParam String title,
                                              @RequestParam String content) {
        return scheduleService.updateSchedule(id, title, content);
    }

    // 일정 제목만 수정
    @PatchMapping("/{id}/title")
    public ScheduleResponseDto updateScheduleTitle(@PathVariable Long id,
                                                   @RequestParam String title) {
        return scheduleService.updateTitle(id, title, null);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}
