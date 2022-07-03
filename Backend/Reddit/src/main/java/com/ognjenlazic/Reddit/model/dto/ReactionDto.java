package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Reaction;
import com.ognjenlazic.Reddit.model.enumeration.ReactionType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDto{
    private Long reactionId;
    private ReactionType reactionType;
    private LocalDateTime timestamp;
    private Long userId;
    private Long postId;
    private Long commentId;

    public ReactionDto(Reaction reaction){
        // Way of the creating dto when there is some of the null fields in database, object
        if (reaction.getComment() == null) {
            this.commentId = null;
        } else {
            this.commentId = reaction.getComment().getCommentId();
        }

        if (reaction.getPost() == null) {
            this.postId = null;
        } else {
            this.postId = reaction.getPost().getId();
        }

        this.reactionId = reaction.getReactionId();
        this.reactionType = reaction.getReactionType();
        this.timestamp = reaction.getTimestamp();
        this.userId = reaction.getUser().getUserId();
    }
}
