package com.crona.calendar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisitDTO {
    private Integer userId;
    private Integer eventId;
    private String timeFrom;
    private EventDTO event;
}
