package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Community;
import com.ognjenlazic.Reddit.model.entity.Rule;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {
    private Long communityId;
    private String name;
    private String description;
    private String suspendedReason;
    private Date creationDate;
    private boolean suspended;
    private Long moderatorId;
    private Set<Rule> rules;
    //private UserDto moderator;

    public CommunityDto(Community community){
        // Currently other DTOs are null because this is testing case...
        this(community.getCommunityId(),community.getName(),community.getDescription(),community.getSuspendedReason(),community.getCreationDate(), community.isSuspended(),  community.getModerator().getUserId(), community.getRules());
    }
}
