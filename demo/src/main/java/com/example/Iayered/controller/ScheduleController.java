package com.example.Iayered.controller;

import com.example.Iayered.dto.ScheduleResponseDto;
import com.example.Iayered.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 등록
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponseDto createSchedule(@RequestParam String name,
                                              @RequestParam String password,
                                              @RequestParam String content,
                                              @RequestParam String title) {
        return scheduleService.createSchedule(name, password, content, title);
    }

    // 전체 일정 조회
    @GetMapping("/")
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // 특정 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
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
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}
