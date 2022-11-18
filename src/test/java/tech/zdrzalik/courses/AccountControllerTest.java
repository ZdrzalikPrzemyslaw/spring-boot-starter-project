package tech.zdrzalik.courses;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.jdbc.JdbcTestUtils;
import tech.zdrzalik.courses.DTO.Request.RegisterAccountDTO;
import tech.zdrzalik.courses.controllers.AccountController;
import tech.zdrzalik.courses.exceptions.AccountInfoException;
import tech.zdrzalik.courses.services.AccountService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AccountControllerTest {
    @Autowired
    private AccountController accountController;

    @BeforeAll
    static void beforeAll(@Autowired AccountService accountService) {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("anonymousUser", "key", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
        accountService.registerAccount("a@b.com", "123456789", "fName", "lName");
    }

    @Test
    void successfulRegister() {
        accountController.registerAccount(new RegisterAccountDTO("b@c.com", "Password123!", "fNameTest", "lNameTest"));
    }

    @Test
    void failRegisterExistingMail() {
        RegisterAccountDTO registerAccountDTO = new RegisterAccountDTO("a@b.com", "Password123!", "fNameTest", "lNameTest");
        Assertions.assertThrows(AccountInfoException.class, () -> {
            accountController.registerAccount(registerAccountDTO);
        });
    }

    @AfterAll
    static void afterAll(@Autowired JdbcTemplate jdbcTemplate) {
        SecurityContextHolder.getContext().setAuthentication(null);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "access_levels", "user_info", "account_info", "table_metadata");
    }

}
