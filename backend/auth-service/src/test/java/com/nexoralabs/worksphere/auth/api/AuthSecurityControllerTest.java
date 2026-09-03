package com.nexoralabs.worksphere.auth.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.nexoralabs.worksphere.auth.config.SecurityConfig;
import com.nexoralabs.worksphere.auth.config.AuthProperties;
import com.nexoralabs.worksphere.auth.security.JsonAccessDeniedHandler;
import com.nexoralabs.worksphere.auth.security.JsonAuthenticationEntryPoint;
import com.nexoralabs.worksphere.auth.security.JwtAuthenticationFilter;
import com.nexoralabs.worksphere.auth.security.JwtService;
import com.nexoralabs.worksphere.auth.service.AuthService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AuthController.class, properties = "auth.jwt.secret=unit-test-secret-that-is-at-least-32-characters-long")
@Import({SecurityConfig.class, AuthProperties.class, JwtService.class, JwtAuthenticationFilter.class,
    JsonAuthenticationEntryPoint.class, JsonAccessDeniedHandler.class})
class AuthSecurityControllerTest {
    @Autowired MockMvc mvc;
    @MockBean AuthService authService;
    @Test void meRequiresValidJwtAuthentication() throws Exception {
        mvc.perform(get("/api/v1/auth/me")).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value("UNAUTHORIZED"));
    }
}