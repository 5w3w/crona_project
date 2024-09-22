package com.crona.calendar_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class DeleteDTO {
    @NotNull(message = "userId must be notNull")
    private Integer userId;
}
