package com.neeraj.assistant.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(

    @NotBlank(message = "Token is required")
    String token, 
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at atleast 8 characters")
    String password

) {
    

}
