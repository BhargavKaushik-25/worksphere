package com.nexoralabs.worksphere.auth.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private final Jwt jwt = new Jwt();
    private final Lockout lockout = new Lockout();

    public Jwt getJwt() { return jwt; }
    public Lockout getLockout() { return lockout; }

    public static class Jwt {
        private String secret;
        private Duration accessTokenExpiration = Duration.ofMinutes(15);
        private Duration refreshTokenExpiration = Duration.ofDays(7);
        public String getSecret() { return secret; }
        public void setSecret(String secret) { this.secret = secret; }
        public Duration getAccessTokenExpiration() { return accessTokenExpiration; }
        public void setAccessTokenExpiration(Duration value) { this.accessTokenExpiration = value; }
        public Duration getRefreshTokenExpiration() { return refreshTokenExpiration; }
        public void setRefreshTokenExpiration(Duration value) { this.refreshTokenExpiration = value; }
    }

    public static class Lockout {
        private int maxFailedAttempts = 5;
        private Duration duration = Duration.ofMinutes(15);
        public int getMaxFailedAttempts() { return maxFailedAttempts; }
        public void setMaxFailedAttempts(int value) { this.maxFailedAttempts = value; }
        public Duration getDuration() { return duration; }
        public void setDuration(Duration value) { this.duration = value; }
    }
}