package com.ognjenlazic.Reddit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ognjenlazic.Reddit.model.enumeration.ReactionType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reaction{

    @Id
    @Column(name = "reaction_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionId;

    @NotNull
    @Column(name = "reaction_type", unique = false, nullable = false)
    private ReactionType reactionType;

    @NotNull
    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDateTime timestamp;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment")
    private Comment comment;
}