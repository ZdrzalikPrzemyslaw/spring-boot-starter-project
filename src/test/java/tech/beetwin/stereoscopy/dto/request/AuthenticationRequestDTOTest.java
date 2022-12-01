package tech.beetwin.stereoscopy.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.stereoscopy.common.I18nCodes;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class AuthenticationRequestDTOTest {

    private Validator validator;
    AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();

    private void makeValidDTO() {
        authenticationRequestDTO.setEmail("test@test.com");
        authenticationRequestDTO.setPassword("1234567890");
    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        makeValidDTO();
    }

    @Test
    void getValidTest() {
        var validate = validator.validate(authenticationRequestDTO);
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    void emailNullTest() {
        authenticationRequestDTO.setEmail(null);
        Assertions.assertNull(authenticationRequestDTO.getEmail());
        var validate = validator.validate(authenticationRequestDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(authenticationRequestDTOConstraintViolation -> {
            if (authenticationRequestDTOConstraintViolation.getMessage().equals(I18nCodes.EMAIL_NULL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void passwordNullTest() {
        authenticationRequestDTO.setPassword(null);
        Assertions.assertNull(authenticationRequestDTO.getPassword());
        var validate = validator.validate(authenticationRequestDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(authenticationRequestDTOConstraintViolation -> {
            if (authenticationRequestDTOConstraintViolation.getMessage().equals(I18nCodes.PASSWORD_NULL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

}
