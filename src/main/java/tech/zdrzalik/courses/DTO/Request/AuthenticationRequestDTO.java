package tech.zdrzalik.courses.DTO.Request;

import org.springframework.validation.BindingResult;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.controllers.AuthenticationController;
import tech.zdrzalik.courses.controllers.admin.AdminController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * This class defines a POJO used as a DTO to transfer login information.
 * Used as a param in {@link AuthenticationController#authenticate(AuthenticationRequestDTO)} and {@link AdminController#authenticate(AuthenticationRequestDTO, BindingResult, HttpServletResponse)}
 */
public class AuthenticationRequestDTO {
    @NotNull(message = I18nCodes.EMAIL_NULL)
    private String email;
    @NotNull(message = I18nCodes.PASSWORD_NULL)
    private String password;

    public AuthenticationRequestDTO() {
    }

    public AuthenticationRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public AuthenticationRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationRequestDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
