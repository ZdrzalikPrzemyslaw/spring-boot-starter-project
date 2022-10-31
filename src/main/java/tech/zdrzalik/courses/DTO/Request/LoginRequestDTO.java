package tech.zdrzalik.courses.DTO.Request;

public class LoginRequestDTO {
    private String email;
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
