package com.example.Iayered.service;

import com.example.Iayered.dto.ScheduleResponseDto;
import com.example.Iayered.entity.Schedule;
import com.example.Iayered.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String title, String content){

        if(title == null || content == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values. ");
        }

        // 내용 수정
        int updatedRow = scheduleRepository.updateSchedule(id, title, content);
        // 수정된 row가 0개 라면
        if (updatedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified");
        }

        // 수정된 메모 조회
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
    }

    @Transactional
    @Override // 재정의
    public ScheduleResponseDto updateTitle(Long id, String title, String content){

        // 값이 없을때를 대비해서 검사
        if(title == null || content != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }

        // memo 제목 수정
        int updatedRow = scheduleRepository.updateTitle(id, title);
        // 수정된 row가 0개 라면
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        // 수정된 메모 조회
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
    }

    @Override
    public void deleteSchedule(Long id){
        int deletedRow = scheduleRepository.deleteSchedule(id);
        if (deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

}
