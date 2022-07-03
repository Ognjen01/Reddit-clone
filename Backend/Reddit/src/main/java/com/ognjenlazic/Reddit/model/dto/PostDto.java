package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime creationDate;
    private String imagePath;
    private Long communityId;
    private Long userId;
    private Long flairId;

    public PostDto(Post post){
        if(post.getFlairs() == null){
            this.flairId = null;
        } else {
            this.flairId = post.getFlairs().getFlairId();
        }
        this.id = post.getId();
        this.title = post.getTitle();
        this.text =  post.getText();
        this.creationDate = post.getCreationDate();
        this.imagePath = post.getImagePath();
        this.communityId = post.getCommunity().getCommunityId();
        this.userId =post.getUser().getUserId();
//        this(post.getId(),post.getTitle(), post.getText(), post.getCreationDate(), post.getImagePath(), post.getCommunity().getCommunityId(), post.getUser().getUserId(), post.getFlairs().getFlairId());
    }

}
