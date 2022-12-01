package tech.beetwin.stereoscopy.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.stereoscopy.common.I18nCodes;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class EditUserInfoDTOTest {

    private Validator validator;
    EditUserInfoDTO editUserInfoDTO = new EditUserInfoDTO();

    private void makeValidDTO() {
        editUserInfoDTO.setEmail("test@test.com");
        editUserInfoDTO.setFirstName("Test");
        editUserInfoDTO.setLastName("Testowy");
        editUserInfoDTO.setEnabled(true);
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
