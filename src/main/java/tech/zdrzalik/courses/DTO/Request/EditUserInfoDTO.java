package tech.zdrzalik.courses.DTO.Request;

import tech.zdrzalik.courses.common.I18nCodes;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EditUserInfoDTO {
    @Min(value = 0, message = I18nCodes.EMAIL_INVALID_SIZE)
    @Max(value = 64, message = I18nCodes.EMAIL_INVALID_SIZE)
    @NotNull(message = I18nCodes.EMAIL_NULL)
    private String email;
    @NotNull(message = I18nCodes.ENABLED_NULL)
    private Boolean enabled;
    @Min(value = 0, message = I18nCodes.FIRST_NAME_INVALID_SIZE)
    @Max(value = 64, message = I18nCodes.FIRST_NAME_INVALID_SIZE)
    @NotNull(message = I18nCodes.FIRST_NAME_INVALID_SIZE)
    private String firstName;
    @Min(value = 0, message = I18nCodes.LAST_NAME_INVALID_SIZE)
    @Max(value = 64, message = I18nCodes.LAST_NAME_INVALID_SIZE)
    @NotNull(message = I18nCodes.LAST_NAME_NULL)
    private String lastName;

    public EditUserInfoDTO() {
    }

    public String getEmail() {
        return email;
    }

    public EditUserInfoDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public EditUserInfoDTO setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public EditUserInfoDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EditUserInfoDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
