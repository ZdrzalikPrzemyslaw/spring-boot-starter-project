package tech.zdrzalik.courses.DTO.Request;

import tech.zdrzalik.courses.common.I18nCodes;

import javax.validation.constraints.NotNull;

public class LoginRequestDTO {
    @NotNull(message = I18nCodes.EMAIL_NULL)
    private String email;
    @NotNull(message = I18nCodes.PASSWORD_NULL)
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public LoginRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequestDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
