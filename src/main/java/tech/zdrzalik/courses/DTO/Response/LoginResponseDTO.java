package tech.zdrzalik.courses.DTO.Response;

import tech.zdrzalik.courses.DTO.Request.LoginRequestDTO;
import tech.zdrzalik.courses.DTO.Request.RegisterAccountDTO;
import tech.zdrzalik.courses.controllers.AccountController;

/**
 * This class defines a POJO used as a DTO to transfer information after successful authentication.
 * Used as a return type in {@link tech.zdrzalik.courses.controllers.AuthenticationController#authenticate(LoginRequestDTO)}
 */
public class LoginResponseDTO extends MessageResponseDTO {

    public LoginResponseDTO() {
        super();
    }
    private String token;

    public String getToken() {
        return token;
    }

    public LoginResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }

    public LoginResponseDTO setMessage(String message) {
        super.setMessage(message);
        return this;
    }
}
