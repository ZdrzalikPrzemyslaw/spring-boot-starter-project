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
public class RegisterAccountDTO extends UserAccountDTO {

    @NotNull(message = I18nCodes.PASSWORD_NULL)
    @Length(min = 8, message = I18nCodes.PASSWORD_INVALID_SIZE)
    private String password;

    public RegisterAccountDTO(String email, String password, String firstName, String lastName) {
        super(email, firstName, lastName);
        this.password = password;
    }

    public RegisterAccountDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public RegisterAccountDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
