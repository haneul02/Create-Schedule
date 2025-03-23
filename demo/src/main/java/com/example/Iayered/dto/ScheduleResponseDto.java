package com.example.Iayered.dto;

import com.example.Iayered.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String content;
    private String title;
    private LocalDateTime creation;
    private LocalDateTime reision;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.content = schedule.getContent();
        this.title = schedule.getTitle();
        this.creation = schedule.getCreation();
        this.reision = schedule.getReision();
    }
}
