package com.ognjenlazic.Reddit.model.entity;

import com.ognjenlazic.Reddit.model.enumeration.ReportReason;
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
public class Report{

    @Id
    @Column(name = "report_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @NotNull
    @Column(name = "report_reason", nullable = false)
    private ReportReason reason;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user", nullable = false)
    private User byUser;

    @ManyToOne
    @JoinColumn(name = "comment")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @NotNull
    @Column(name = "accepted", nullable = false)
    private boolean accepted;
}