package tech.beetwin.stereoscopy;

import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.beetwin.stereoscopy.dto.request.AuthenticationRequestDTO;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.controllers.AuthenticationController;
import tech.beetwin.stereoscopy.exceptions.AuthorizationErrorException;
import tech.beetwin.stereoscopy.model.TableMetadata.TableMetadataRepository;
import tech.beetwin.stereoscopy.services.AccountService;
import tech.beetwin.stereoscopy.services.TableMetadataService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationController authenticationController;

    @BeforeAll
    static void beforeAll(@Autowired AccountService accountService) {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        accountService.registerAccount("admin@userowy.com", "Password123", "admin", "userowy");
        accountService.setAccountAdminRole("admin@userowy.com", true);
        accountService.registerAccount("admin@zablokowany.com", "Password123", "admin", "zablokowany");
        accountService.setAccountAdminRole("admin@userowy.com", true);
        accountService.setAccountEnabled("admin@zablokowany.com", false);
    }

    @AfterAll
    static void afterAll(@Autowired JdbcTemplate jdbcTemplate, @Autowired TableMetadataService tableMetadataService) {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        tableMetadataService.wipeAllMetadataCreatedModified();
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "access_levels", "user_info", "account_info", "table_metadata");
        TestUtils.wipeAuth(SecurityContextHolder.getContext()); }

    @BeforeEach
    void setUp() {
        TestUtils.setAnonymousAuth(SecurityContextHolder.getContext());
    }

    @Test
    void authSuccessful() {
        var res = authenticationController.authenticate(new AuthenticationRequestDTO().setEmail("admin@userowy.com").setPassword("Password123"));
        Assertions.assertNotNull(res);
        var status = res.getStatusCode();
        var body = res.getBody();
        Assertions.assertEquals(HttpStatus.OK, status);
        Assertions.assertNotNull(body);
        Assertions.assertNotNull(body.getMessage());
        Assertions.assertNotNull(body.getToken());
        Assertions.assertEquals(I18nCodes.AUTHENTICATION_SUCCESS, body.getMessage());
    }

    @Test
    void authFailNotExist() {
        var dto = new AuthenticationRequestDTO().setEmail("admin@nieistniejacy.com").setPassword("Password123");
        Assertions.assertThrows(AuthorizationErrorException.class, () -> {
            authenticationController.authenticate(dto);
        }, I18nCodes.INVALID_CREDENTIALS);
    }

    @Test
    void authFailInvalidPassword() {
        var dto = new AuthenticationRequestDTO().setEmail("admin@nieistniejacy.com").setPassword("Password1234");
        Assertions.assertThrows(AuthorizationErrorException.class, () -> {
            authenticationController.authenticate(dto);
        }, I18nCodes.INVALID_CREDENTIALS);
    }

    @Test
    void authFailNotEnabled() {
        var dto = new AuthenticationRequestDTO().setEmail("admin@zablokowany.com").setPassword("Password123");
        Assertions.assertThrows(AuthorizationErrorException.class, () -> {
            authenticationController.authenticate(dto);
        }, I18nCodes.ACCOUNT_DISABLED);
    }

    @Test
    void authSuccessfulMvc() throws Exception {
        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content("{\"email\": \"admin@userowy.com\",\"password\": \"Password123\"}")).andExpect(status().isOk()).andDo(result -> {
            String res = result.getResponse().getContentAsString();
            net.minidev.json.JSONObject object = (net.minidev.json.JSONObject) new JSONParser().parse(res);
            Assertions.assertEquals(I18nCodes.AUTHENTICATION_SUCCESS, object.get("message"));
        });
    }

}
