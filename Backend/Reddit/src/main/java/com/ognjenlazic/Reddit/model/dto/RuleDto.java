package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Rule;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleDto{
    private Long ruleId;
    private String description;
    private Long communityId;

    public RuleDto (Rule rule){
        // Currently other DTOs are null because this is testing case...
        this(rule.getRuleId(), rule.getDescription(), rule.getCommunity().getCommunityId());
    }
}
