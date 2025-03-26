package com.example.demo.Iayered.dto;

import com.example.demo.Iayered.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String email;
    private String name;
    private String content;
    private String title;
    private LocalDateTime creation;
    private LocalDateTime revision;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.email = schedule.getEmail();
        this.name = schedule.getName();
        this.content = schedule.getContent();
        this.title = schedule.getTitle();
        this.creation = schedule.getCreation();
        this.revision = schedule.getRevision();
    }
    
}
