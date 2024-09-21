package com.crona.calendar_backend.entity;

import com.crona.calendar_backend.dto.CreateEventDTO;
import com.crona.calendar_backend.dto.EditEventDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "Events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EventEntity {
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss");

    @Id
    @SequenceGenerator(name = "events_id_seq", sequenceName = "events_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_id_seq")
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "organizator")
    private User organizator;
    private String location;
    private Integer maxCapacity;
    private Integer currCap;
    private Timestamp timeFrom;
    private Timestamp timeTo;

    public static EventEntity create(CreateEventDTO createEventDTO, User organizator) {
        try {
            return EventEntity.builder()
                    .name(createEventDTO.getName())
                    .organizator(organizator)
                    .location(createEventDTO.getLocation())
                    .maxCapacity(createEventDTO.getMaxCapacity())
                    .currCap(createEventDTO.getCurrCap())
                    .timeFrom(new Timestamp(TIMESTAMP_FORMAT.parse(createEventDTO.getTimeFrom()).getTime()))
                    .timeTo(new Timestamp(TIMESTAMP_FORMAT.parse(createEventDTO.getTimeTo()).getTime()))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(EditEventDTO editEventDTO, User organizator) {
        this.name = editEventDTO.getName();
        this.organizator = organizator;
        this.location = editEventDTO.getLocation();
        this.maxCapacity = editEventDTO.getMaxCapacity();
        this.currCap = editEventDTO.getCurrCap();
        try {
            this.timeFrom = new Timestamp(TIMESTAMP_FORMAT.parse(editEventDTO.getTimeFrom()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            this.timeTo = new Timestamp(TIMESTAMP_FORMAT.parse(editEventDTO.getTimeTo()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
