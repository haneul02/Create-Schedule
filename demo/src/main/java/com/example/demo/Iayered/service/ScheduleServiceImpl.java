package com.example.demo.Iayered.service;

import com.example.demo.Iayered.dto.ScheduleRequestDto;
import com.example.demo.Iayered.dto.ScheduleResponseDto;
import com.example.demo.Iayered.dto.UserDto;
import com.example.demo.Iayered.entity.Schedule;
import com.example.demo.Iayered.exception.IdException;
import com.example.demo.Iayered.exception.PasswordIncorrectException;
import com.example.demo.Iayered.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 일정 저장
    @Transactional
    @Override
    public void createSchedule(ScheduleRequestDto requestDto){
        if (requestDto.getName() == null || requestDto.getPassword() == null || requestDto.getContent() == null || requestDto.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "모든 필드는 필수입니다.");
        }

        scheduleRepository.saveSchedule(requestDto);
    }

    // 전체 일정 조회
    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponseDto> getAllSchedules(int page, int size) {
        return scheduleRepository.findAllSchedules(page, size);
    }

    // 단일 일정 조회
    @Transactional(readOnly = true)
    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findScheduleById(id);
        if (schedule.isEmpty()) {
            throw new IdException();
        }
        return new ScheduleResponseDto(schedule.get());
    }

    // password, email이 맞아야 제목, 내용 수정 가능
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String title, String content, String password, String email){

        if(title == null || content == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목 & 내용은 필수입니다.");
        }

        UserDto userdto = scheduleRepository.findEmailAndPassword(id);

        // 비밀번호 검증
        if (!(userdto.getEmail().equals(email) && userdto.getPassword().equals(password))) {
            throw new PasswordIncorrectException();
        }
        // 내용 수정
        int updatedRow = scheduleRepository.updateSchedule(id, title, content);
        
        // 수정된 row가 0개 라면
        if (updatedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정 오류");
        }

        // 수정된 메모 조회
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
    }

    // 내용만 수정
    @Transactional
    @Override
    public ScheduleResponseDto updateTitle(Long id, String title, String password, String email){
        if(title == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 필수입니다.");
        }

        // 스케줄 조회
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new IdException());

        UserDto userdto = scheduleRepository.findEmailAndPassword(id);

        // 비밀번호 검증
        if (!(userdto.getEmail().equals(email) && userdto.getPassword().equals(password))) {
            throw new PasswordIncorrectException();
        }

        // memo 제목 수정
        int updatedRow = scheduleRepository.updateTitle(id, title);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "변경된 데이터가 없습니다.");
        }

        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
    }


    // 일정 삭제
    @Transactional
    @Override
    public void deleteSchedule(Long id, String password, String email){
        // 스케줄 조회
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new IdException());

        UserDto userdto = scheduleRepository.findEmailAndPassword(id);

        // 비밀번호 검증
        if (!(userdto.getEmail().equals(email) && userdto.getPassword().equals(password))) {
            throw new PasswordIncorrectException();
        }
        int deletedRow = scheduleRepository.deleteSchedule(id);
        if (deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 일정을 찾을 수 없습니다.");
        }
    }

}
