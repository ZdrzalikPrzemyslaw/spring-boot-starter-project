package tech.zdrzalik.courses.DTO.Response;

import tech.zdrzalik.courses.DTO.Request.AuthenticationRequestDTO;

/**
 * This class defines a POJO used as a DTO to transfer information after successful authentication.
 * Used as a return type in {@link tech.zdrzalik.courses.controllers.AuthenticationController#authenticate(AuthenticationRequestDTO)}
 */
public class AuthenticationResponseDTO extends MessageResponseDTO {

    public AuthenticationResponseDTO() {
        super();
    }
    private String token;

    public String getToken() {
        return token;
    }

    public AuthenticationResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public AuthenticationResponseDTO setMessage(String message) {
        super.setMessage(message);
        return this;
    }
}
