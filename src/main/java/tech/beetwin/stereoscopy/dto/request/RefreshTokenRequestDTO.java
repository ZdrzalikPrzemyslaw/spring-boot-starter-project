package tech.beetwin.stereoscopy.dto.request;

import tech.beetwin.stereoscopy.common.I18nCodes;

import javax.validation.constraints.NotBlank;

public class RefreshTokenRequestDTO {
    @NotBlank(message = I18nCodes.REFRESH_TOKEN_NULL)
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public RefreshTokenRequestDTO setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public RefreshTokenRequestDTO() {
    }

}
