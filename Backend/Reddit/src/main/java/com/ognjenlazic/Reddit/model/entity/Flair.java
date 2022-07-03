package com.ognjenlazic.Reddit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Flair {

    @Id
    @Column(name = "flair_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flairId;

    @NotNull
    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @ManyToMany
    @JoinColumn(name = "comunities")
    private Set<Community> communities;

    @OneToMany
    @JoinColumn(name = "post")
    @JsonIgnore
    private Set<Post> posts;
}
