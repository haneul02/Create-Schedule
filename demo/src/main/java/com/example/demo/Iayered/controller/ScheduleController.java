package com.example.demo.Iayered.controller;

import com.example.demo.Iayered.dto.ScheduleRequestDto;
import com.example.demo.Iayered.dto.ScheduleResponseDto;
import com.example.demo.Iayered.service.ScheduleService;
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
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        System.out.println("name = " + requestDto.getName());
        System.out.println("password = " + requestDto.getPassword());
        System.out.println("content = " + requestDto.getContent());
        System.out.println("title = " + requestDto.getTitle());

        return scheduleService.createSchedule(
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getContent(),
                requestDto.getTitle()
        );
    }

    // 전체 일정 조회
    @GetMapping("/all")
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // 특정 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    // 일정 전체 수정 (제목 + 내용 + 비밀번호)
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id,
                                              @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(
                id,
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getPassword()
        );
    }

    // 일정 제목만 수정 (제목 + 비밀번호)
    @PatchMapping("/{id}/title")
    public ScheduleResponseDto updateScheduleTitle(@PathVariable Long id,
                                                   @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateTitle(
                id,
                requestDto.getTitle(),
                requestDto.getPassword()
        );
    }

    // 일정 삭제 (id + 비밀번호)
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id,
                               @RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto.getPassword());
    }
}
