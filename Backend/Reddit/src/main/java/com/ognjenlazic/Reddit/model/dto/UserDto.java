package com.ognjenlazic.Reddit.model.dto;

import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.model.enumeration.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String displayName;
    private String description;
    private UserType userType;
    private LocalDateTime registrationDate;

    public UserDto(User user){
        this(user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getAvatar(),
                user.getDisplayName(),
                user.getDescription(),
                user.getUserType(),
                user.getRegistrationDate());
    }
}
