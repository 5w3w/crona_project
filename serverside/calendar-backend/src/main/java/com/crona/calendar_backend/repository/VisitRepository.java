package com.crona.calendar_backend.repository;

import com.crona.calendar_backend.dto.EventDTO;
import com.crona.calendar_backend.dto.VisitDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Transactional
@Repository
public class VisitRepository { // очень на скорую руку

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss");

    @PersistenceContext
    private final EntityManager entityManager;

    public VisitRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*public List<VisitDTO> getAllVisits() {
        return ((List<Object[]>) entityManager.createNativeQuery("SELECT * FROM Visits").getResultList())
                .stream()
                .map(r -> new VisitDTO(
                        (Integer) r[0],
                        (Integer) r[1],
                        (Timestamp) r[2]))
                .toList();
    }

    public VisitDTO getVisit(Integer id) {
        Object[] result = (Object[]) entityManager.createNativeQuery("SELECT * FROM Visits WHERE id = " + id).getSingleResult();
        return new VisitDTO(
                        (Integer) result[0],
                        (Integer) result[1],
                        (Timestamp) result[2]);
    }*/

    public List<VisitDTO> getVisitsInOneHour(Integer userId) {
        return ((List<Object[]>) entityManager.createNativeQuery(
                "SELECT v.event_id, v.time_from, " +
                "e.id, e.name, e.organizator, e.location, e.max_capacity, e.curr_cap, e.time_from, e.time_to " +
                "FROM visits v " +
                "JOIN events e ON e.id = v.event_id " +
                "WHERE age(v.time_from, NOW()) <= INTERVAL '1 hour 5 minutes' AND age(v.time_from, NOW()) > INTERVAL '1 hour'" +
                "AND v.user_id = " + userId)
                .getResultList())
                .stream()
                .map(r -> new VisitDTO(
                        userId,
                        (Integer) r[0],
                        TIMESTAMP_FORMAT.format((Timestamp) r[1]),
                        new EventDTO(
                                (Integer) r[2],
                                (String) r[3],
                                (Integer) r[4],
                                (String) r[5],
                                (Integer) r[6],
                                (Integer) r[7],
                                TIMESTAMP_FORMAT.format((Timestamp) r[8]),
                                TIMESTAMP_FORMAT.format((Timestamp) r[9])
                            )
                        )
                ).toList();
    }

    public List<VisitDTO> getVisitsInTwoMinutes(Integer userId) {
        return ((List<Object[]>) entityManager.createNativeQuery(
                "SELECT v.event_id, v.time_from, " +
                "e.id, e.name, e.organizator, e.location, e.max_capacity, e.curr_cap, e.time_from, e.time_to " +
                "FROM visits v " +
                "JOIN events e ON e.id = v.event_id " +
                "WHERE age(v.time_from, NOW()) <= INTERVAL '2 minutes' AND age(v.time_from, NOW()) > INTERVAL '10 seconds'" +
                "AND v.user_id = " + userId)
                .getResultList())
                .stream()
                .map(r -> new VisitDTO(
                        userId,
                        (Integer) r[0],
                        TIMESTAMP_FORMAT.format((Timestamp) r[1]),
                        new EventDTO(
                                (Integer) r[2],
                                (String) r[3],
                                (Integer) r[4],
                                (String) r[5],
                                (Integer) r[6],
                                (Integer) r[7],
                                TIMESTAMP_FORMAT.format((Timestamp) r[8]),
                                TIMESTAMP_FORMAT.format((Timestamp) r[9])
                            )
                        )
                ).toList();
    }

    public void createVisit(int userId, int eventId, String timeFrom) {
        entityManager.createNativeQuery("INSERT INTO visits (user_id, event_id, time_from) " +
                "VALUES (:userId, :eventId, TO_TIMESTAMP(:timeFrom, 'YYYY.MM.DD\"T\"HH:MI:SS'))")
                .setParameter("userId", userId)
                .setParameter("eventId", eventId)
                .setParameter("timeFrom", timeFrom)
                .executeUpdate();
    }

    public void deleteByEvent(Integer eventId) {
        entityManager.createNativeQuery("DELETE FROM Visits " +
                "WHERE event_id = " + eventId).executeUpdate();
    }
}
