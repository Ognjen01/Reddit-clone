package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Comment;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private String text;
    private LocalDate timestamp;
    private boolean isDeleted;
    private Long userId;
    private Long postId;
    private Long repliesToCommentId;

    public CommentDto(Comment comment) {
        if (comment.getComment() == null) {
            this.repliesToCommentId = null;
        } else {
            this.repliesToCommentId = comment.getComment().getCommentId();
        }

        if (comment.getPost() == null) {
            this.postId = null;
        } else {
            this.postId = comment.getPost().getId();
        }

        this.commentId = comment.getCommentId();
        this.text = comment.getText();
        this.timestamp = comment.getTimestamp();
        this.isDeleted = comment.isDeleted();
        this.userId = comment.getUser().getUserId();
    }
}
