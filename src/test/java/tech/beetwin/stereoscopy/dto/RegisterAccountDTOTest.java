package tech.beetwin.stereoscopy.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.stereoscopy.DTO.Request.RegisterAccountDTO;
import tech.beetwin.stereoscopy.common.I18nCodes;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class RegisterAccountDTOTest {
    private Validator validator;
    RegisterAccountDTO registerAccountDTO = new RegisterAccountDTO();

    private void makeValidDTO() {
        registerAccountDTO.setEmail("test@test.com");
        registerAccountDTO.setPassword("1234567890");
        registerAccountDTO.setFirstName("Test");
        registerAccountDTO.setLastName("Testowy");
    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        makeValidDTO();
    }

    @Test
    void getValidTest() {
        var validate = validator.validate(registerAccountDTO);
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    void emailNullTest() {
        registerAccountDTO.setEmail(null);
        Assertions.assertNull(registerAccountDTO.getEmail());
        var validate = validator.validate(registerAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.EMAIL_NULL)) {
                correctMessage[0] = true;
            }
        });
       Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void notAnEmailTest() {
        String email = "test";
        registerAccountDTO.setEmail(email);
        Assertions.assertEquals(registerAccountDTO.getEmail(), email);
        var validate = validator.validate(registerAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.NOT_AN_EMAIL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void passwordNullTest() {
        registerAccountDTO.setPassword(null);
        Assertions.assertNull(registerAccountDTO.getPassword());
        var validate = validator.validate(registerAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.PASSWORD_NULL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }


    @Test
    void passwordInvalidSizeTest() {
        String password = "12345";
        registerAccountDTO.setPassword(password);
        Assertions.assertEquals(registerAccountDTO.getPassword(), password);
        var validate = validator.validate(registerAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.PASSWORD_INVALID_SIZE)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void firstNameNullTest() {
        registerAccountDTO.setFirstName(null);
        Assertions.assertNull(registerAccountDTO.getFirstName());
        var validate = validator.validate(registerAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.FIRST_NAME_NULL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void lastNameNullTest() {
        registerAccountDTO.setLastName(null);
        Assertions.assertNull(registerAccountDTO.getLastName());
        var validate = validator.validate(registerAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.LAST_NAME_NULL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }
}
