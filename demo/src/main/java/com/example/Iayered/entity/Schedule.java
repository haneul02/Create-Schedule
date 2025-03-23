package com.example.Iayered.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Schedule {


    private Long id;
    private String name;
    private String password;
    private String content;
    private String title;
    private LocalDateTime creation;
    private LocalDateTime reision;

    public Schedule(String name, String password){
        this.name = name;
        this.password = password;
        this.creation = LocalDateTime.now();
        this.reision = LocalDateTime.now();
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
        this.reision = LocalDateTime.now();
    }

    public Schedule(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void update(String title) {
        this.title = title;
        this.reision = LocalDateTime.now();
    }

}
