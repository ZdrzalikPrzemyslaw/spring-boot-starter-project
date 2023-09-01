package tech.beetwin.template.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.template.common.I18nCodes;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class UserAccountDTOTest {

    UserAccountDTO userAccountDTO = new UserAccountDTO();
    private Validator validator;

    private void makeValidDTO() {
        userAccountDTO.setEmail("test@test.com");
        userAccountDTO.setFirstName("Test");
        userAccountDTO.setLastName("Testowy");
    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        makeValidDTO();
    }

    @Test
    void getValidTest() {
        var validate = validator.validate(userAccountDTO);
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    void emailNullTest() {
        userAccountDTO.setEmail(null);
        Assertions.assertNull(userAccountDTO.getEmail());
        var validate = validator.validate(userAccountDTO);
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
        userAccountDTO.setEmail(email);
        Assertions.assertEquals(userAccountDTO.getEmail(), email);
        var validate = validator.validate(userAccountDTO);
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
    void emailInvalidSize() {
        String email = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        userAccountDTO.setEmail(email);
        Assertions.assertEquals(userAccountDTO.getEmail(), email);
        var validate = validator.validate(userAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.EMAIL_INVALID_SIZE)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void firstNameNullTest() {
        userAccountDTO.setFirstName(null);
        Assertions.assertNull(userAccountDTO.getFirstName());
        var validate = validator.validate(userAccountDTO);
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
    void firstNameInvalidSize() {
        String firstName = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        userAccountDTO.setFirstName(firstName);
        Assertions.assertEquals(userAccountDTO.getFirstName(), firstName);
        var validate = validator.validate(userAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.FIRST_NAME_INVALID_SIZE)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void lastNameNullTest() {
        userAccountDTO.setLastName(null);
        Assertions.assertNull(userAccountDTO.getLastName());
        var validate = validator.validate(userAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.LAST_NAME_NULL)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

    @Test
    void lastNameInvalidSize() {
        String lastName = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        userAccountDTO.setLastName(lastName);
        Assertions.assertEquals(userAccountDTO.getLastName(), lastName);
        var validate = validator.validate(userAccountDTO);
        Assertions.assertFalse(validate.isEmpty());
        final boolean[] correctMessage = {false};
        validate.forEach(registerAccountDTOConstraintViolation -> {
            if (registerAccountDTOConstraintViolation.getMessage().equals(I18nCodes.LAST_NAME_INVALID_SIZE)) {
                correctMessage[0] = true;
            }
        });
        Assertions.assertTrue(correctMessage[0]);
    }

}
