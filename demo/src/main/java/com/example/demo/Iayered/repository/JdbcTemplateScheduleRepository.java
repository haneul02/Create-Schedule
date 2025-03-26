package com.example.demo.Iayered.repository;

import com.example.demo.Iayered.dto.ScheduleRequestDto;
import com.example.demo.Iayered.dto.ScheduleResponseDto;
import com.example.demo.Iayered.dto.UserDto;
import com.example.demo.Iayered.entity.Schedule;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository

public class JdbcTemplateScheduleRepository implements ScheduleRepository{ // alt + 엔터 누르면 override(인텔리제이가 빨간줄로 오류 났다고 해주면 앞에 누르면 오류 해결방법을 찾아줘)

    private final JdbcTemplate jdbcTemplate;

    // 생성자
    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 일정 저장
    @Override
    public void saveSchedule(ScheduleRequestDto requestDto) {
        //새로운사람인지 아닌지 검사
        String sql = "SELECT id FROM user WHERE email = ? AND password = ?";
        Integer userid = jdbcTemplate.query(sql,
                new Object[]{requestDto.getEmail(), requestDto.getPassword()},
                (rs) -> {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                    return null;
                });

        String now = java.time.LocalDateTime.now().toString();

        if(userid==null){
            SimpleJdbcInsert userInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("user")
                    .usingGeneratedKeyColumns("id")
                    .usingColumns("name","email","password", "creation", "revision");


            Map<String,Object> userParams = new HashMap<>();
            userParams.put("name", requestDto.getName());
            userParams.put("email", requestDto.getEmail());
            userParams.put("password", requestDto.getPassword());
            userParams.put("creation", now);
            userParams.put("revision", now);

            Number newUserid = userInsert.executeAndReturnKey(new MapSqlParameterSource(userParams));
            userid = newUserid.intValue();
        }

        // userid를 포함한 데이터를 Schedule테이블에 넣어
        SimpleJdbcInsert userInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id")
                .usingColumns("content", "title", "creation", "revision", "userid");


        Map<String,Object> parameters = new HashMap<>();
        parameters.put("content", requestDto.getContent());
        parameters.put("title", requestDto.getTitle());
        parameters.put("creation", now);
        parameters.put("revision", now);
        parameters.put("userid",userid);

        Number scheduleid = userInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));



    }

    // 전체 일정 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        String sql = "SELECT s.id, s.content, s.title,s.creation, s.revision, u.name, s.userid, u.email FROM schedule s join user u on s.userid = u.id";
        List<Schedule> schedules = jdbcTemplate.query(sql, scheduleRowMapper());

        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    // ID로 일정 조회
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        String sql = "SELECT s.id, s.content, s.title,s.creation, s.revision, u.name, s.userid, u.email FROM schedule s join user u on s.userid = u.id WHERE s.id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return result.stream().findFirst();
    }

    @Override
    public UserDto findEmailAndPassword(Long id){
        String sql = "SELECT u.email, u.password FROM user u JOIN schedule s ON u.id = s.userid WHERE s.id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs,rowNum)-> new UserDto(
                    rs.getString("email"),
                    rs.getString("password")
                ),id
        );
    }



    // RowMapper 정의
    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("content"),
                rs.getString("title"),
                rs.getTimestamp("creation").toLocalDateTime(),
                rs.getTimestamp("revision").toLocalDateTime()
        );
    }

//    private RowMapper<Schedule> userRowMapper() {
//        return (rs, rowNum) -> new Schedule(
//                rs.getLong("id"),
//                rs.getString("name"),
//                rs.getString("email"),
//                rs.getString("password"),
//                rs.getTimestamp("creation").toLocalDateTime(),
//                rs.getTimestamp("revision").toLocalDateTime()
//        );
//    }

    // 내용 전체 수정
    @Override
    public int updateSchedule(Long id, String title, String contents) {
        String now = java.time.LocalDateTime.now().toString();
        return jdbcTemplate.update(
                "UPDATE schedule SET title = ?, content = ?, revision = ? WHERE id = ?",
                title, contents, now, id
        );
    }

    // 제목 수정
    @Override
    public int updateTitle(Long id, String title) {
        String now = java.time.LocalDateTime.now().toString();
        return jdbcTemplate.update(
                "UPDATE schedule SET title = ?, revision = ? WHERE id = ?",
                title, now, id
        );
    }

    // 일정 삭제
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }
}