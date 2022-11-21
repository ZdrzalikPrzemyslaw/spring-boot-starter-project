package tech.zdrzalik.courses;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.jdbc.JdbcTestUtils;
import tech.zdrzalik.courses.DTO.Request.AuthenticationRequestDTO;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.exceptions.AuthorizationErrorException;
import tech.zdrzalik.courses.exceptions.EntityNotFoundException;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoRepository;
import tech.zdrzalik.courses.services.AccountService;

import java.rmi.UnexpectedException;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.)
class AccountServiceTest {
    private final static String USER_EMAIL = "admin@userowy.com";
    private final static String BLOCKED_USER_EMAIL = "user@zablokowany.com";
    private final static String USER_PASSWORD = "Password123";
    private final static String USER_FIRST_NAME = "Admin";
    private final static String USER_LAST_NAME = "Userowy";
    private final static String BLOCKED_USER_LAST_NAME = "Zablokowany";
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @BeforeAll
    static void beforeAll(@Autowired AccountService accountService) {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        accountService.registerAccount(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);
        accountService.registerAccount(BLOCKED_USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, BLOCKED_USER_LAST_NAME);
        accountService.setAccountAdminRole(USER_EMAIL, true);
        accountService.setAccountEnabled(BLOCKED_USER_EMAIL, false);
        TestUtils.setAnonymousAuth(SecurityContextHolder.getContext());
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
    void authenticateTest() {
        var res = accountService.authenticate(new AuthenticationRequestDTO().setEmail(USER_EMAIL).setPassword(USER_PASSWORD));
        Assertions.assertNotNull(res);
        Assertions.assertTrue(Strings.isNotBlank(res));
    }

    @Test
    void authenticateFailInvalidPasswordTest() {
        var req = new AuthenticationRequestDTO().setEmail(USER_EMAIL).setPassword(USER_PASSWORD + "0");
        Assertions.assertThrows(AuthorizationErrorException.class,
                () -> accountService.authenticate(req),
                I18nCodes.INVALID_CREDENTIALS);
    }

    @Test
    void authenticateFailAccountDisabledTest() {
        var req = new AuthenticationRequestDTO().setEmail(BLOCKED_USER_EMAIL).setPassword(USER_PASSWORD);
        Assertions.assertThrows(AuthorizationErrorException.class,
                () -> accountService.authenticate(req),
                I18nCodes.ACCOUNT_DISABLED);
    }



    @Test
    void findAccountByEmail() {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        var entity = accountService.findByEmail(USER_EMAIL);
        Assertions.assertNotNull(entity);
        Assertions.assertTrue(entity.isEnabled());
        Assertions.assertEquals(USER_EMAIL, entity.getEmail());
        Assertions.assertEquals(USER_FIRST_NAME, entity.getUserInfoEntity().getFirstName());
        Assertions.assertEquals(USER_LAST_NAME, entity.getUserInfoEntity().getLastName());
    }

    @Test
    void findAccountByEmailFailNotAuth() {
        Assertions.assertThrows(AccessDeniedException.class, () -> {
            accountService.findByEmail(USER_EMAIL);
        });
    }

    @Test
    void editAccountSuccessful() {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        var accountInfoEntity = accountService.findByEmail(USER_EMAIL);
        accountService.editAccount(
                accountInfoEntity.getId(),
                accountInfoEntity.getEmail(),
                accountInfoEntity.isEnabled(),
                accountInfoEntity.getUserInfoEntity().getFirstName(),
                "newLastName");
        var accountInfoEntity2 = accountService.findByEmail(USER_EMAIL);

        Assertions.assertNotEquals(
                accountInfoEntity.getUserInfoEntity().getLastName(),
                accountInfoEntity2.getUserInfoEntity().getLastName());
        Assertions.assertEquals("newLastName", accountInfoEntity2.getUserInfoEntity().getLastName());

        //cleanup
        accountService.editAccount(
                accountInfoEntity.getId(),
                accountInfoEntity.getEmail(),
                accountInfoEntity.isEnabled(),
                accountInfoEntity.getUserInfoEntity().getFirstName(),
                USER_LAST_NAME);
    }

    @Test
    void editAccountFailNotAuth() {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        var accountInfoEntity = accountService.findByEmail(USER_EMAIL);
        var id = accountInfoEntity.getId();
        var email = accountInfoEntity.getEmail();
        var enabled = accountInfoEntity.isEnabled();
        var firstName = accountInfoEntity.getUserInfoEntity().getFirstName();
        Assertions.assertNotNull(accountInfoEntity);
        TestUtils.setAnonymousAuth(SecurityContextHolder.getContext());
        Assertions.assertThrows(AccessDeniedException.class, () -> {
            accountService.editAccount(
                    id,
                    email,
                    enabled,
                    firstName,
                    "newLastName");
        });
    }

    @Test
    void editAccountFailNotFound() throws UnexpectedException {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        var invalidId = accountInfoRepository.findFirstByOrderByIdDesc().orElseThrow(() -> new UnexpectedException("Entity should exist")).getId() + 10;
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            accountService.editAccount(
                    invalidId,
                    USER_EMAIL,
                    true,
                    USER_FIRST_NAME,
                    "newLastName");
        }, I18nCodes.ENTITY_NOT_FOUND);
    }

}
