package com.ognjenlazic.Reddit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post{

    @Id
    @Column(name = "post_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull
    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    //@NotNull
    @Column(name = "imagePath", nullable = true)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "community", nullable = false)
    private Community community;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToMany(cascade = {ALL}, fetch = LAZY)
    @JoinColumn(name = "reactions")
    private Set<Reaction> reaction;

    @OneToMany(cascade = {ALL}, fetch = LAZY)
    @JoinColumn(name = "comments")
    private Set<Comment> comments;

    @OneToMany(cascade = {ALL}, fetch = LAZY)
    @JoinColumn(name = "reports")
    @JsonIgnore
    private Set<Report> reports;

    @ManyToOne
    @JoinColumn(name = "flairs")
    private Flair flairs;
}