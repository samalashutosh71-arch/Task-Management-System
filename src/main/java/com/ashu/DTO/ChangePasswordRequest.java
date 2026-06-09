package com.ashu.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class ChangePasswordRequest {

    private String oldPassword;

    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
        message = "Password must contain one uppercase letter, one number, one special character and minimum 8 characters"
    )
    private String newPassword;

    private String confirmPassword;

    
}
