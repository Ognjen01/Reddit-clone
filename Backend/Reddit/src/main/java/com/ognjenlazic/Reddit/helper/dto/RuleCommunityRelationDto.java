package com.ognjenlazic.Reddit.helper.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleCommunityRelationDto {
    private Long ruleId;
    private Long communityId;
}
