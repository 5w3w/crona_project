package com.crona.calendar_backend.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class EditEventDTO {
    @NotNull(message = "userId must be notNull")
    private Integer userId;
    private String name;
    private String location;
    private Integer maxCapacity;
    private Integer currCap;
    private String timeFrom;
    private String timeTo;

    @AssertTrue
    private boolean isNameValid() {
        if (this.name != null) return !name.isBlank();
        else return true;
    }

    @AssertTrue
    private boolean isLocationValid() {
        if (this.location != null) return !location.isBlank();
        else return true;
    }

    @AssertTrue
    private boolean isTimeFromValid() { // TODO формат даты
        if (this.timeFrom != null) return !timeFrom.isBlank();
        else return true;
    }

    @AssertTrue
    private boolean isTimeToValid() {
        if (this.timeTo != null) return !timeTo.isBlank();
        else return true;
    }
}
