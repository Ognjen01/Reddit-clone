package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Report;
import com.ognjenlazic.Reddit.model.enumeration.ReportReason;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto{
    private Long reportId;
    private LocalDateTime timestamp;
    private ReportReason reason;
    private Long userId;
    private Long commentId;
    private Long postId;
    private boolean accepted;

    public ReportDto(Report report){
        if (report.getPost() == null){
            this.postId = null;
        } else {
            this.postId = report.getPost().getId();
        }

        if (report.getComment() == null){
            this.commentId = null;
        } else {
            this.commentId = report.getComment().getCommentId();
        }

        this.reportId = report.getReportId();
        this.timestamp = report.getTimestamp();
        this.reason = report.getReason();
        this.userId = report.getByUser().getUserId();
        this.accepted = report.isAccepted();
    }
}
