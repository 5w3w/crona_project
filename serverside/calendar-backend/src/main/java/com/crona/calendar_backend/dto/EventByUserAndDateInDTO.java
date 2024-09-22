package com.crona.calendar_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class EventByUserAndDateInDTO {
    @NotNull(message = "userId must be notNull")
    private Integer userId;
    @NotNull(message = "month must be notNull")
    private int month;
    @NotNull(message = "year must be notNull")
    private int year;
}
