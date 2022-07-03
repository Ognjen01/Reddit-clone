package com.ognjenlazic.Reddit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ognjenlazic.Reddit.model.enumeration.UserType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User{

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    @Size(min = 4, message = "Username must have at least 4 characters.")
    @Column(name = "username", nullable = false)
    private String username;

    @NonNull
    @Size(min = 4, message = "Password must have at least 4 characters.")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "avatar", nullable = false)
    private String avatar;

    @NotNull
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "user_type", nullable = false)
    private UserType userType;

//    @NotNull
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @ManyToMany
    @JoinColumn(name = "communities")
    @JsonIgnore
    private Set<Community> communities;

    @OneToMany
    @JoinColumn(name = "posts")
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany
    @JoinColumn(name = "comments")
    @JsonIgnore
    private Set<Comment> comments;

    @OneToMany
    @JoinColumn(name = "reactions")
    @JsonIgnore
    private Set<Post> reactions;

    @OneToMany
    @JoinColumn(name = "banneds")
    @JsonIgnore
    private Set<Banned> banneds;

    @OneToOne
    @JoinColumn(name = "moderates")
    @JsonIgnore
    private Community moderatesComunity;
}