package tech.beetwin.stereoscopy.DTO.Response;

import tech.beetwin.stereoscopy.DTO.Request.AuthenticationRequestDTO;
import tech.beetwin.stereoscopy.controllers.AuthenticationController;

/**
 * This class defines a POJO used as a DTO to transfer information after successful authentication.
 * Used as a return type in {@link AuthenticationController#authenticate(AuthenticationRequestDTO)}
 */
public class AuthenticationResponseDTO extends BasicMessageResponseDTO {

    private String token;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long validDuration;

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
