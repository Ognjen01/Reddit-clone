package com.ognjenlazic.Reddit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @Column(name = "comment_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @NotNull
    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @NotNull
    @Column(name = "is_deleted", unique = false, nullable = false)
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "repliesTo")
    private Comment repliesTo;

    @OneToMany
    @JoinColumn(name = "reports")
    @JsonIgnore
    private Set<Report> reports = new HashSet<Report>();

    // Lista komentara koji komentarišu ovaj komentar
    @OneToMany
    @JoinColumn(name = "reactions")
    @JsonIgnore
    private Set<Reaction> reactions = new HashSet<Reaction>();

    @ManyToOne
    @JoinColumn(name = "comment")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;
}