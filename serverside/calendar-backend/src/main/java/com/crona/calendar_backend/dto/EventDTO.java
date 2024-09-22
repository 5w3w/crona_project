package com.crona.calendar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventDTO {
    private Integer id;
    private String name;
    private Integer organizator;
    private String location;
    private Integer maxCapacity;
    private Integer currCap;
    private String timeFrom;
    private String timeTo;
}
