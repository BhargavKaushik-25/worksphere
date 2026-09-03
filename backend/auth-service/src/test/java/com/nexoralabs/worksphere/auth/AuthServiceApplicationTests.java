package com.nexoralabs.worksphere.auth;

import com.nexoralabs.worksphere.auth.repository.AuthUserRepository;
import com.nexoralabs.worksphere.auth.repository.AuthUserRoleRepository;
import com.nexoralabs.worksphere.auth.repository.LoginAttemptRepository;
import com.nexoralabs.worksphere.auth.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = {
    "auth.jwt.secret=unit-test-secret-that-is-at-least-32-characters-long",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
class AuthServiceApplicationTests {
    @MockBean AuthUserRepository authUserRepository;
    @MockBean AuthUserRoleRepository authUserRoleRepository;
    @MockBean LoginAttemptRepository loginAttemptRepository;
    @MockBean RefreshTokenRepository refreshTokenRepository;

    @Test
    void contextLoads() {
    }
}
