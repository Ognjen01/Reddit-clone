package com.ognjenlazic.Reddit.helper.dto;

import com.ognjenlazic.Reddit.model.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String email;
    @NotBlank
    private String avatar;
    @NotBlank
    private String displayName;
    @NotBlank
    private String description;

    public RegisterDto(User user){
        this(user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getAvatar(),
                user.getDisplayName(),
                user.getDescription());
    }
}
