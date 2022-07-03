package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.Flair;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlairDto {
    private Long flairId;
    private String name;

    public FlairDto(Flair flair){
        this(
                flair.getFlairId(),
                flair.getName()
        );
    }
}
