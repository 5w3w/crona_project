package com.crona.calendar_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Integer id;
    private String name;
    private String password;
}
