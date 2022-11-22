package tech.beetwin.stereoscopy;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.beetwin.stereoscopy.controllers.AccountController;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoRepository;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

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
    void workingRegistration() throws Exception {
        String email = "test@test.pl";
        String firstName = "Test1";
        String lastName = "Test2";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", "test123");
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        String data = jsonObject.toString();

        mockMvc.perform(post("/account/register").contentType("application/json").content(data)).andExpect(status().isOk());

        List<AccountInfoEntity> accounts = accountInfoRepository.findAll().stream().filter(account -> Objects.equals(account.getEmail(), email)).toList();
        assertThat(accounts).hasSize(1);
        assertThat(accounts.get(0).getEmail()).isEqualTo(email);
        assertThat(accounts.get(0).getEmail()).isEqualTo(email);
        assertThat(accounts.get(0).getUserInfoEntity().getFirstName()).isEqualTo(firstName);
        assertThat(accounts.get(0).getUserInfoEntity().getLastName()).isEqualTo(lastName);
    }
}