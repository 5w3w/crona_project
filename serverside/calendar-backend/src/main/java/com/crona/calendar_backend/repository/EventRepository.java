package com.crona.calendar_backend.repository;

import com.crona.calendar_backend.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM events WHERE organizator = ?1 AND " + // organizator = userId
            "DATE_PART('year', time_from) = ?2 AND " + // year = input year
            "((TO_CHAR(time_from, 'W') = '4' OR TO_CHAR(time_from, 'W') = '5' AND DATE_PART('month', time_from) = ?3 - 1) " + // 4я неделя и предыдущий месяц
            "OR (TO_CHAR(time_from, 'W') = '1' AND DATE_PART('month', time_from) = ?3 + 1) " + // или 1 я неделя и следующий месяц
            "OR (DATE_PART('month', time_from) = ?3))") // или текущий месяц
    List<EventEntity> findEventsByUserIdAndDate(Integer userId, int year, int month);
}
