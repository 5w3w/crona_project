package com.crona.calendar_backend.service;

import com.crona.calendar_backend.dto.CreateEventDTO;
import com.crona.calendar_backend.dto.EditEventDTO;
import com.crona.calendar_backend.dto.EventDTO;
import com.crona.calendar_backend.entity.EventEntity;
import com.crona.calendar_backend.entity.User;
import com.crona.calendar_backend.exception.BadRequestException;
import com.crona.calendar_backend.repository.EventRepository;
import com.crona.calendar_backend.repository.UserRepository;
import com.crona.calendar_backend.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, VisitRepository visitRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
    }

    public List<EventDTO> getEventByUserIdAndDate(Integer userId, int month, int year) {
        return eventRepository.findEventsByUserIdAndDate(userId, year, month).stream().map(e -> e.toDTO()).toList();
    }

    @Transactional
    public void createEvent(CreateEventDTO createEventDTO) {
        Optional<User> organizatorOpt = userRepository.findById(createEventDTO.getUserId());
        if (organizatorOpt.isEmpty())
            throw new BadRequestException("Такого пользователя не существует");


        eventRepository.save(EventEntity.create(createEventDTO, organizatorOpt.get()));
    }

    @Transactional
    public void deleteEvent(Integer userId, Integer eventId) {
        Optional<EventEntity> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty())
            throw new BadRequestException("Event с таким id не существует в базе данных");
        EventEntity event = eventOpt.get();

        if (!event.getOrganizator().getId().equals(userId)) {
            throw new BadRequestException("Пользователь не является организатором данного event");
        }

        visitRepository.deleteByEvent(eventId);
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public EventDTO editEvent(Integer id, EditEventDTO editEventDTO) {
        Optional<EventEntity> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty())
            throw new BadRequestException("Event с таким id не существует в базе данных");
        EventEntity event = eventOpt.get();

        Optional<User> userOpt = userRepository.findById(editEventDTO.getUserId());
        if (userOpt.isEmpty())
            throw new BadRequestException("Такого пользователя не существует");
        if (!event.getOrganizator().getId().equals(userOpt.get().getId()))
            throw new BadRequestException("Пользователь не является организатором данного event");

        event.edit(editEventDTO, userOpt.get());
        return eventRepository.save(event).toDTO();
    }
}
