package tech.beetwin.stereoscopy.DTO.Request;

import org.hibernate.validator.constraints.Length;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.controllers.AccountController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * This class defines a POJO used as a DTO to transfer information required to create a new account.
 * Used as a param in {@link AccountController#registerAccount(RegisterAccountDTO)}
 */
public class RegisterAccountDTO {
    @NotNull(message = I18nCodes.EMAIL_NULL)
    @Email(message = I18nCodes.NOT_AN_EMAIL, regexp = ".*.@.*")
    private String email;
    @NotNull(message = I18nCodes.PASSWORD_NULL)
    @Length(min = 8, message = I18nCodes.PASSWORD_INVALID_SIZE)
    private String password;
    @NotNull(message = I18nCodes.FIRST_NAME_NULL)
    private String firstName;
    @NotNull(message = I18nCodes.LAST_NAME_NULL)
    private String lastName;

    public RegisterAccountDTO(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public RegisterAccountDTO() {
    }

    public String getEmail() {
        return email;
    }

    public RegisterAccountDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterAccountDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterAccountDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterAccountDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
