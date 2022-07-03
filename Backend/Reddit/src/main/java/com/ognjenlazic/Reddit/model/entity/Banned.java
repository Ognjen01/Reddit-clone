package com.ognjenlazic.Reddit.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Banned{

    @Id
    @Column(name = "ban_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long banId;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private LocalDate timestamp;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User by;

    @ManyToOne
    @JoinColumn(name = "community", nullable = false)
    private Community community;
}