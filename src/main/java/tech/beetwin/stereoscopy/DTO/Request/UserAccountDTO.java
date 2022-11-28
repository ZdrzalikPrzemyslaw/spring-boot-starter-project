package tech.beetwin.stereoscopy.DTO.Request;

import org.hibernate.validator.constraints.Length;
import tech.beetwin.stereoscopy.common.I18nCodes;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserAccountDTO {

    @NotNull(message = I18nCodes.EMAIL_NULL)
    @Email(message = I18nCodes.NOT_AN_EMAIL, regexp = ".*.@.*")
    @Length(min = 0, max = 64, message = I18nCodes.EMAIL_INVALID_SIZE)
    private String email;
    @NotNull(message = I18nCodes.FIRST_NAME_NULL)
    @Length(min = 0, max = 64, message = I18nCodes.FIRST_NAME_INVALID_SIZE)
    private String firstName;
    @NotNull(message = I18nCodes.LAST_NAME_NULL)
    @Length(min = 0, max = 64, message = I18nCodes.LAST_NAME_INVALID_SIZE)
    private String lastName;

    public UserAccountDTO(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserAccountDTO(){}

    public String getEmail() {
        return email;
    }

    public UserAccountDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserAccountDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAccountDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

}
