package com.osio.market.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String email;
    private String password;
    private String username;
    private String phone;
    private String address;
    private String verificationCode;
}
