package tech.beetwin.stereoscopy.dto.request;

import org.springframework.validation.BindingResult;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.controllers.admin.AdminController;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.stereoscopy.utils.ApplicationContextUtils;
import tech.beetwin.stereoscopy.utils.VersionValidation;

import javax.validation.constraints.NotNull;

/**
 * This class defines a POJO used as a DTO to transfer basic user info.
 * Used as a param in {@link AdminController#editUser(Long, EditUserInfoDTO, BindingResult)}
 */
public class EditUserInfoDTO extends UserAccountDTO {

    @NotNull(message = I18nCodes.ENABLED_NULL)
    private Boolean enabled;

    @VersionValidation
    private String versionToken;

    public EditUserInfoDTO() {
    }

    public EditUserInfoDTO(AccountInfoEntity accountInfoEntity, String token) {
        super(accountInfoEntity.getEmail(), accountInfoEntity.getUserInfoEntity().getFirstName(), accountInfoEntity.getUserInfoEntity().getFirstName());
        enabled = accountInfoEntity.isEnabled();
        versionToken = token;

    }

    public String getVersionToken() {
        return versionToken;
    }

    public Long getVersion(){
        return ApplicationContextUtils.getVersionJWTUtils().getVersion(versionToken);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public EditUserInfoDTO setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public EditUserInfoDTO setVersionToken(String versionToken) {
        this.versionToken = versionToken;
        return this;
    }
}
