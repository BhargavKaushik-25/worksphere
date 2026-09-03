package com.nexoralabs.worksphere.auth.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

public final class AuthDtos {
    private AuthDtos() { }
    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) { }
    public record LogoutRequest(@NotBlank String refreshToken) { }
    public record TokenResponse(String accessToken, String refreshToken, long expiresIn) { }
    public record UserResponse(UUID id, String email, String username, List<String> roles) { }
}