package com.crona.calendar_backend.controller;

import com.crona.calendar_backend.dto.CreateVisitDTO;
import com.crona.calendar_backend.dto.NearestVisitsDTO;
import com.crona.calendar_backend.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/visit")
@Validated
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVisit(@RequestBody @Valid CreateVisitDTO createVisitDTO) {
        visitService.createVisit(createVisitDTO);
    }

    @GetMapping("/getNearestVisits/{userId}")
    public List<NearestVisitsDTO> getNearestVisits(@PathVariable Integer userId) {
        return visitService.getNearestVisits(userId);
    }
}
