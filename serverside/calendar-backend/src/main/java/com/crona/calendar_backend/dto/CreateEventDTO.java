package com.crona.calendar_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CreateEventDTO {
    @NotNull (message = "userId must be notNull")
    private Integer userId;
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotNull
    private Integer maxCapacity;
    @NotNull
    private Integer currCap;
    @NotBlank
    private String timeFrom;
    @NotBlank
    private String timeTo;
}
