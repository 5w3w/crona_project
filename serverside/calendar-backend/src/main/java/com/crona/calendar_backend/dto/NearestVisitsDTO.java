package com.crona.calendar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NearestVisitsDTO {
    private String startTime;
    private List<VisitDTO> visits;
}
