package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Banned;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BannedDto {
    private Long banId;
    private LocalDate timestamp;
    private Long userId;
    private Long communityId;

    public BannedDto(Banned banned){
        this(
                banned.getBanId(),
                banned.getTimestamp(),
                banned.getBy().getUserId(),
                banned.getCommunity().getCommunityId()
        );
    }
}
