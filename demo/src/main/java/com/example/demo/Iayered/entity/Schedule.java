package com.example.demo.Iayered.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class Schedule {


    private Long id;
    private String name;                // 작성자
    private String email;               // 이메일
    private String password;            // 비밀번호
    private String content;             // 제목
    private String title;               // 내용
    private LocalDateTime creation;     // 저장 시간
    private LocalDateTime revision;      // 수정 시간

    public Schedule(Long id, String name, String email, String content, String title, LocalDateTime creation, LocalDateTime revision ){
        this.id = id;
        this.name = name;
        this.email = email;
        this.content = content;
        this.title = title;
        this.creation = LocalDateTime.now();
        this.revision = LocalDateTime.now();
    }

    public Schedule(String name, String password, String content, String title){
        this.name = name;
        this.password = password;
        this.content = content;
        this.title = title;
        this.creation = LocalDateTime.now();
        this.revision = LocalDateTime.now();
    }

    private String nowDate(){
        return java.time.LocalDate.now().toString();
    }
}
