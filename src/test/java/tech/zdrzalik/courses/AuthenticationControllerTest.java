package tech.zdrzalik.courses;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.zdrzalik.courses.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll(@Autowired AccountService accountService) {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        accountService.registerAccount("admin@userowy.com", "Password123", "admin", "userowy");
        accountService.setAccountAdminRole("admin@userowy.com", true);
    }

    @AfterAll
    static void afterAll(@Autowired JdbcTemplate jdbcTemplate) {
        TestUtils.wipeAuth(SecurityContextHolder.getContext());
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "access_levels", "user_info", "account_info", "table_metadata");
    }

    @BeforeEach
    void setUp() {
        TestUtils.setAnonymousAuth(SecurityContextHolder.getContext());
    }

    @Test
    void name() {

    }
}
