package tech.zdrzalik.courses;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoRepository;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class RegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

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

        mockMvc.perform(post("/account/register", 42L)
                        .contentType("application/json")
                        .content(data))
                .andExpect(status().isOk());

        List<AccountInfoEntity> accounts = accountInfoRepository.findAll().stream().filter(account -> Objects.equals(account.getEmail(), email)).toList();
        assertThat(accounts.size()).isEqualTo(1);
        assertThat(accounts.get(0).getEmail()).isEqualTo(email);
        assertThat(accounts.get(0).getEmail()).isEqualTo(email);
        assertThat(accounts.get(0).getUserInfoEntity().getFirstName()).isEqualTo(firstName);
        assertThat(accounts.get(0).getUserInfoEntity().getLastName()).isEqualTo(lastName);
    }
}