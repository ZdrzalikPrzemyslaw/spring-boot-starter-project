package tech.zdrzalik.courses.DTO.Request;

import org.springframework.validation.BindingResult;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.controllers.AuthenticationController;
import tech.zdrzalik.courses.controllers.admin.AdminController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * This class defines a POJO used as a DTO to transfer login information.
 * Used as a param in {@link AuthenticationController#authenticate(LoginRequestDTO)} and {@link AdminController#authenticate(LoginRequestDTO, BindingResult, HttpServletResponse)}
 */
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
