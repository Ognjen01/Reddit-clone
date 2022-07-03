package com.ognjenlazic.Reddit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Community {

    @Id
    @Column(name = "community_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @NotNull
    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "suspended_reason", unique = false, nullable = true)
    private String suspendedReason;

    @NotNull
    @Column(name = "creation_date", unique = false, nullable = false)
    private Date creationDate;

    @NotNull
    @Column(name = "suspended", unique = false, nullable = false)
    private boolean suspended;


    @ManyToMany
    @JoinColumn(name = "flairs")
    @JsonIgnore
    private Set<Flair> flairs;

    @OneToMany
    @JoinColumn(name = "rules")
    @JsonIgnore
    private Set<Rule> rules;

    @OneToMany
    @JoinColumn(name = "posts")
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany
    @JoinColumn(name = "banned")
    @JsonIgnore
    private Set<Banned> banned;

    @ManyToMany
    @JoinColumn(name = "users")
    @JsonIgnore
    private Set<User> users;

    @OneToOne
    @JoinColumn(name = "moderator")
    private User moderator;
}