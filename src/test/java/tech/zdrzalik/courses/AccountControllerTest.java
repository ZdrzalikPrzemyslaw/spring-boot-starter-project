package tech.zdrzalik.courses;

import net.minidev.json.parser.JSONParser;
import org.junit.Assert;
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
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.zdrzalik.courses.DTO.Request.RegisterAccountDTO;
import tech.zdrzalik.courses.DTO.Response.BasicMessageResponseDTO;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.controllers.AccountController;
import tech.zdrzalik.courses.exceptions.AccountInfoException;
import tech.zdrzalik.courses.services.AccountService;

import javax.validation.constraints.AssertTrue;
import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.)
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private AccountController accountController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll(@Autowired AccountService accountService) {
        TestUtils.setAnonymousAuth(SecurityContextHolder.getContext());
        accountService.registerAccount("a@b.com", "123456789", "fName", "lName");
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
    void successfulRegister() {
        var response = accountController.registerAccount(new RegisterAccountDTO("b@c.com", "Password123!", "fNameTest", "lNameTest"));
        Assertions.assertTrue(response.hasBody());
        var body = response.getBody();
        var status = response.getStatusCode();
        Assertions.assertNotNull(body);
        Assertions.assertNotNull(body.getMessage());
        Assertions.assertEquals(I18nCodes.ACCOUNT_CREATED_SUCCESSFULLY, body.getMessage());
        Assertions.assertEquals(HttpStatus.OK, status);
    }

    @Test
    void failRegisterExistingMail() {
        RegisterAccountDTO registerAccountDTO = new RegisterAccountDTO("a@b.com", "Password123!", "fNameTest", "lNameTest");
        Assertions.assertThrows(AccountInfoException.class, () -> {
            accountController.registerAccount(registerAccountDTO);
        });
    }

    @Test
    void successfulRegisterMvc() throws Exception {
        mockMvc.perform(post("/account/register").contentType(MediaType.APPLICATION_JSON).content("{\"email\": \"admin@userowy.com\",\"password\": \"Password123\", \"firstName\":  \"fName\", \"lastName\":  \"lName\"}")).andExpect(status().isOk()).andDo(result -> {
            String res = result.getResponse().getContentAsString();
            net.minidev.json.JSONObject object = (net.minidev.json.JSONObject) new JSONParser().parse(res);
            Assertions.assertEquals(I18nCodes.ACCOUNT_CREATED_SUCCESSFULLY, object.get("message"));
        });
    }

}
