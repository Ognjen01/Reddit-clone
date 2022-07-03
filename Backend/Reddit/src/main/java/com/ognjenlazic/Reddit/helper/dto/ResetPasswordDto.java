package com.ognjenlazic.Reddit.helper.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
