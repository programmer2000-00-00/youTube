package com.example.dto.auth;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthDTO {
    @Size(min = 3, max = 50)
    private String password;
    @Size(min = 3, max = 50)
    private String email;

    public AuthDTO(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
