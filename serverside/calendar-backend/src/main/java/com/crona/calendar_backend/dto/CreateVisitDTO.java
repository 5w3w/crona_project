package com.crona.calendar_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CreateVisitDTO {
    @NotNull(message = "userId must be notNull")
    private Integer userId;
    @NotNull(message = "eventId must be notNull")
    private Integer eventId;
    @NotBlank(message = "timeFrom must be notNull")
    private String timeFrom;
}
