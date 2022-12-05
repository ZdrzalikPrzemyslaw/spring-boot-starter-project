package tech.beetwin.stereoscopy;

import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.beetwin.stereoscopy.controllers.admin.AdminController;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoRepository;
import tech.beetwin.stereoscopy.services.AccountService;
import tech.beetwin.stereoscopy.services.TableMetadataService;

import java.text.MessageFormat;

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
    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @BeforeAll
    static void beforeAll(@Autowired AccountService accountService, @Autowired MockMvc mockMvc) throws Exception {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        accountService.registerAccount("admin@userowy.com", "Password123", "admin", "userowy");
        accountService.setAccountAdminRole("admin@userowy.com", true);

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content("{\"email\": \"admin@userowy.com\",\"password\": \"Password123\"}")).andExpect(status().isOk()).andDo(result -> {
            String res = result.getResponse().getContentAsString();
            net.minidev.json.JSONObject object = (net.minidev.json.JSONObject) new JSONParser().parse(res);
            authToken = MessageFormat.format("Bearer {0}", object.get("authToken").toString());
        });
    }

    @AfterAll
    static void afterAll(@Autowired JdbcTemplate jdbcTemplate, @Autowired TableMetadataService tableMetadataService) {
        TestUtils.setAllRolesAuth(SecurityContextHolder.getContext());
        tableMetadataService.wipeAllMetadataCreatedModified();
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "access_levels", "user_info", "account_info", "table_metadata");
        TestUtils.wipeAuth(SecurityContextHolder.getContext());}

    @BeforeEach
    void setUp() {
        TestUtils.setAnonymousAuth(SecurityContextHolder.getContext());
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(post("/admin/login").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("email", "admin@userowy.com").param("password", "Password123")).andExpect(status().is3xxRedirection());
    }

    //TODO: Poprawić
//    @Test
//    void failLoginInvalidPassword() throws Exception {
//        mockMvc.perform(post("/admin/login").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("email", "admin@userowy.com").param("password", "Password1234")).andExpect(status().isUnauthorized());
//    }

    @Test
    void failLoginNullPassword() throws Exception {
        mockMvc.perform(post("/admin/login").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("email", "admin@userowy.com")).andExpect(status().isBadRequest());
    }

    @Test
    void failGetUsersListUnauthorized() throws Exception {
        mockMvc.perform(get("/admin/user-info")).andExpect(status().isUnauthorized());
    }

    @Test
    void getUsersList() throws Exception {
        mockMvc.perform(get("/admin/user-info").header("authorization", authToken)).andExpect(status().isOk());
    }

    @Test
    void failGetUserInfoUnauthorized() throws Exception {
        mockMvc.perform(get("/admin/user-info/0")).andExpect(status().isUnauthorized());
    }

    @Test
    void failGetUserInfoNotFound() throws Exception {
        long id = 42000;
        var entity = accountInfoRepository.findFirstByOrderByIdDesc();
        if (entity.isPresent()) {
            id = entity.get().getId() + 10L;
        }
        mockMvc.perform(get("/admin/user-info/" + id).header("authorization", authToken)).andExpect(status().isNotFound());
    }

    // TODO: 12/11/2022 Dodać test który pobiera user-info po id i mu sie udaje

    // TODO: 12/11/2022 Dodac test edycji konta

    // TODO: 12/11/2022 W testach sprawdzać czy content odpowiedzi sie zgadza

    @Test
    void getUserInfoSucceed() throws Exception {
        long id = 0;
        var entity = accountInfoRepository.findFirstByOrderByIdDesc();
        if (entity.isPresent()) {
            id = entity.get().getId();
        }
        mockMvc.perform(get("/admin/user-info/" + id).header("authorization", authToken)).andExpect(status().isOk());
    }
}