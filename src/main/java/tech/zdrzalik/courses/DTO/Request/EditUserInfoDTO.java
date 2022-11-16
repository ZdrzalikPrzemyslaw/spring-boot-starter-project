package tech.zdrzalik.courses.DTO.Request;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.controllers.admin.AdminController;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This class defines a POJO used as a DTO to transfer basic user info.
 * Used as a param in {@link AdminController#editUser(Long, EditUserInfoDTO, BindingResult)}
 */
public class EditUserInfoDTO {
    @Length(min = 0, max = 64, message = I18nCodes.EMAIL_INVALID_SIZE)
    @NotBlank(message = I18nCodes.EMAIL_NULL)
    private String email;
    @NotNull(message = I18nCodes.ENABLED_NULL)
    private Boolean enabled;

    @Length(min = 0, max = 64, message = I18nCodes.FIRST_NAME_INVALID_SIZE)
    @NotBlank(message = I18nCodes.FIRST_NAME_NULL)
    private String firstName;
    @Length(min = 0, max = 64, message = I18nCodes.LAST_NAME_INVALID_SIZE)
    @NotBlank(message = I18nCodes.LAST_NAME_NULL)
    private String lastName;

    public EditUserInfoDTO() {
    }

    public EditUserInfoDTO(AccountInfoEntity accountInfoEntity) {
        lastName = accountInfoEntity.getUserInfoEntity().getLastName();
        firstName = accountInfoEntity.getUserInfoEntity().getFirstName();
        enabled = accountInfoEntity.isEnabled();
        email = accountInfoEntity.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public EditUserInfoDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public EditUserInfoDTO setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
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
