package tech.zdrzalik.courses.DTO.Response;

public class LoginResponseDTO {
    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public LoginResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getToken() {
        return token;
    }

    public LoginResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }
}
