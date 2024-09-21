package com.crona.calendar_backend.service;

import com.crona.calendar_backend.dto.CreateVisitDTO;
import com.crona.calendar_backend.dto.NearestVisitsDTO;
import com.crona.calendar_backend.exception.BadRequestException;
import com.crona.calendar_backend.repository.EventRepository;
import com.crona.calendar_backend.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final EventRepository eventRepository;

    public VisitService(VisitRepository visitRepository, EventRepository eventRepository) {
        this.visitRepository = visitRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public List<NearestVisitsDTO> getNearestVisits(Integer userId) {
        return List.of(
                new NearestVisitsDTO("2m", visitRepository.getVisitsInTwoMinutes(userId)),
                new NearestVisitsDTO("1h", visitRepository.getVisitsInOneHour(userId))
        );
    }

    public void createVisit(CreateVisitDTO createVisitDTO) {
        if (!eventRepository.existsById(createVisitDTO.getEventId()))
            throw new BadRequestException("Такого event не существует в базе данных");

        visitRepository.createVisit(createVisitDTO.getUserId(), createVisitDTO.getEventId(), createVisitDTO.getTimeFrom());
    }
}
