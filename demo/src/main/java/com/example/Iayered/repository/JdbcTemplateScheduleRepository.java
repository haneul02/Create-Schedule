package com.example.Iayered.repository;

import com.example.Iayered.dto.ScheduleResponseDto;
import com.example.Iayered.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository

public class JdbcTemplateScheduleRepository implements ScheduleRepository{ // alt + 엔터 누르면 override(인텔리제이가 빨간줄로 오류 났다고 해주면 앞에 누르면 오류 해결방법을 찾아줘)
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 일정 저장
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedule(name, title, content, password, creation, reision) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                schedule.getName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getPassword(),
                schedule.getCreation(),
                schedule.getReision()
        );

        String selectSql = "SELECT * FROM schedule WHERE id = LAST_INSERT_ID()";
        Schedule savedSchedule = jdbcTemplate.queryForObject(selectSql, scheduleRowMapper());

        return new ScheduleResponseDto(savedSchedule);
    }

    // 전체 일정 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        String sql = "SELECT * FROM schedule";
        List<Schedule> schedules = jdbcTemplate.query(sql, scheduleRowMapper());

        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    // ID로 일정 조회
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return result.stream().findFirst();
    }

    // RowMapper 정의
    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("content"),
                rs.getString("title"),
                rs.getTimestamp("creation").toLocalDateTime(),
                rs.getTimestamp("reision").toLocalDateTime()
        );
    }

    // 내용 전체 수정
    @Override
    public int updateSchedule(Long id, String title, String contents) {
        String now = java.time.LocalDate.now().toString();
        return jdbcTemplate.update(
                "UPDATE schedule SET title = ?, content = ?, reision = ? WHERE id = ?",
                title, contents, now, id
        );
    }

    // 제목 수정
    @Override
    public int updateTitle(Long id, String title) {
        String now = java.time.LocalDate.now().toString();
        return jdbcTemplate.update(
                "UPDATE schedule SET title = ?, reision = ? WHERE id = ?",
                title, now, id
        );
    }

    // 일정 삭제
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }
}