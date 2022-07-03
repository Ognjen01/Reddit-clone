package com.ognjenlazic.Reddit.helper.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlairCommunityRelationDto {
    private Long flairId;
    private Long communityId;
}
