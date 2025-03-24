package com.example.demo.Iayered.service;

import com.example.demo.Iayered.dto.ScheduleResponseDto;
import com.example.demo.Iayered.entity.Schedule;
import com.example.demo.Iayered.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    public ScheduleResponseDto createSchedule(String name, String password, String content, String title){
        if (name == null || password == null || content == null || title == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required.");
        }

        Schedule schedule = new Schedule(name, password, content, title);
        return scheduleRepository.saveSchedule(schedule);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Transactional(readOnly = true)
    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findScheduleById(id);
        if (schedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id);
        }
        return new ScheduleResponseDto(schedule.get());
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String title, String content, String password){

        if(title == null || content == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values. ");
        }

        // 스케줄 조회
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        // 비밀번호 검증
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect password.");
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
    @Override
    public ScheduleResponseDto updateTitle(Long id, String title, String password){
        if(title == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title is a required value.");
        }

        // 스케줄 조회
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        // 비밀번호 검증
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect password.");
        }

        // memo 제목 수정
        int updatedRow = scheduleRepository.updateTitle(id, title);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
    }


    @Override
    public void deleteSchedule(Long id, String password){
        // 스케줄 조회
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        // 비밀번호 검증
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect password.");
        }
        int deletedRow = scheduleRepository.deleteSchedule(id);
        if (deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

}
