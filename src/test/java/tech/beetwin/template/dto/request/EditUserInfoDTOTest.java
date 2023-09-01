package tech.beetwin.template.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tech.beetwin.template.common.I18nCodes;
import tech.beetwin.template.model.UserInfo.UserInfoEntity;
import tech.beetwin.template.utils.VersionJWTUtils;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootTest
@AutoConfigureMockMvc
class EditUserInfoDTOTest {

    @Autowired
    VersionJWTUtils jwtUtils;

    private Validator validator;
    EditUserInfoDTO editUserInfoDTO = new EditUserInfoDTO();


    private void makeValidDTO() {
        UserInfoEntity userInfoEntity = new UserInfoEntity().setId(0).setVersion(0L);
        String token = jwtUtils.generateToken(userInfoEntity);

        editUserInfoDTO.setEmail("test@test.com");
        editUserInfoDTO.setFirstName("Test");
        editUserInfoDTO.setLastName("Testowy");
        editUserInfoDTO.setEnabled(true);
        editUserInfoDTO.setVersionToken(token);

    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        makeValidDTO();
    }

    @Test
    void getValidTest() {
        var validate = validator.validate(editUserInfoDTO);
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    void enabledNullTest() {
        editUserInfoDTO.setEnabled(null);
        Assertions.assertNull(editUserInfoDTO.getEnabled());
        var validate = validator.validate(editUserInfoDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.ENABLED_NULL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }
}
