package tech.zdrzalik.courses;

import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.zdrzalik.courses.controllers.admin.AdminController;
import tech.zdrzalik.courses.services.AccountService;

import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    private static String authToken;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdminController adminController;
    @Autowired
    private AccountService accountService;

    @BeforeAll
    static void beforeAll(@Autowired AccountService accountService, @Autowired MockMvc mockMvc) throws Exception {
        accountService.registerAccount("admin@userowy.com", "Password123", "admin", "userowy");

        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("anonymousUser", "key", AuthorityUtils.createAuthorityList("admin", "user")));

        accountService.setAccountAdminRole("admin@userowy.com", true);

        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("anonymousUser", "key", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));


        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"admin@userowy.com\",\"password\": \"Password123\"}"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String res = result.getResponse().getContentAsString();
                    net.minidev.json.JSONObject object = (net.minidev.json.JSONObject) new JSONParser().parse(res);
                    authToken = MessageFormat.format("Bearer {0}", object.get("token").toString());
                });
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "admin@userowy.com")
                        .param("password", "Password123"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void failLoginInvalidPassword() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "admin@userowy.com")
                        .param("password", "Password1234"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void failLoginNullPassword() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "admin@userowy.com"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void failGetUsersListUnauthorized() throws Exception {
        mockMvc.perform(get("/admin/users-list"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUsersList() throws Exception {
        mockMvc.perform(get("/admin/users-list")
                        .header("authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    void failGetUserInfoUnauthorized() throws Exception {
        mockMvc.perform(get("/admin/user-info/0"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void failGetUserInfoNotFound() throws Exception {
        mockMvc.perform(get("/admin/user-info/42000")
                        .header("authorization", authToken))
                .andExpect(status().isNotFound());
    }

    // TODO: 12/11/2022 Dodać test który pobiera user-info po id i mu sie udaje

    // TODO: 12/11/2022 Dodac test edycji konta

    // TODO: 12/11/2022 W testach sprawdzać czy content odpowiedzi sie zgadza

    @AfterAll
    static void afterAll(@Autowired JdbcTemplate jdbcTemplate) {
        SecurityContextHolder.getContext().setAuthentication(null);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "access_levels", "user_info", "account_info", "table_metadata");
    }
}