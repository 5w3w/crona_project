package com.crona.calendar_backend.controller;

import com.crona.calendar_backend.dto.*;
import com.crona.calendar_backend.entity.EventEntity;
import com.crona.calendar_backend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/event")
@Validated
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getSpecificEvents")
    public List<EventDTO> getEventByUserIdAndDate(@RequestBody EventByUserAndDateInDTO inDTO) {
        return eventService.getEventByUserIdAndDate(inDTO.getUserId(), inDTO.getMonth(), inDTO.getYear());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody @Valid CreateEventDTO createEventDTO) {
        eventService.createEvent(createEventDTO);
    }

    @PutMapping("/{eventId}")
    public EventDTO editEvent(@RequestBody @Valid EditEventDTO editEventDTO, @PathVariable Integer eventId) {
        return eventService.editEvent(eventId, editEventDTO);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Integer eventId, @RequestBody @Valid DeleteDTO deleteDTO) {
        eventService.deleteEvent(deleteDTO.getUserId(), eventId);
    }
}
