package tech.beetwin.template.dto.response;

import tech.beetwin.template.dto.request.AuthenticationRequestDTO;
import tech.beetwin.template.controllers.AuthenticationController;

/**
 * This class defines a POJO used as a DTO to transfer information after successful authentication.
 * Used as a return type in {@link AuthenticationController#authenticate(AuthenticationRequestDTO)}
 */
public class AuthenticationResponseDTO extends AbstractMessageResponseDTO<AuthenticationResponseDTO> {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long validDuration;

    private String authToken;
    private String refreshToken;

    public AuthenticationResponseDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public AuthenticationResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthenticationResponseDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthenticationResponseDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuthenticationResponseDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getValidDuration() {
        return validDuration;
    }

    public AuthenticationResponseDTO setValidDuration(Long validDuration) {
        this.validDuration = validDuration;
        return this;
    }

    public String getAuthToken() {
        return authToken;
    }

    public AuthenticationResponseDTO setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthenticationResponseDTO setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Override
    public AuthenticationResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
