package tech.beetwin.stereoscopy.dto.request;

import org.springframework.validation.BindingResult;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.controllers.admin.AdminController;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoEntity;

import javax.validation.constraints.NotNull;

/**
 * This class defines a POJO used as a DTO to transfer basic user info.
 * Used as a param in {@link AdminController#editUser(Long, EditUserInfoDTO, BindingResult)}
 */
public class EditUserInfoDTO extends UserAccountDTO {

    @NotNull(message = I18nCodes.ENABLED_NULL)
    private Boolean enabled;

    public EditUserInfoDTO() {
    }

    public EditUserInfoDTO(AccountInfoEntity accountInfoEntity) {
        super(accountInfoEntity.getEmail(), accountInfoEntity.getUserInfoEntity().getFirstName(), accountInfoEntity.getUserInfoEntity().getFirstName());
        enabled = accountInfoEntity.isEnabled();

    }

    public Boolean getEnabled() {
        return enabled;
    }

    public EditUserInfoDTO setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
