package tech.zdrzalik.courses.DTO.Request;

public class RegisterAccountDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public RegisterAccountDTO(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public RegisterAccountDTO() {
    }

    public String getEmail() {
        return email;
    }

    public RegisterAccountDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterAccountDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterAccountDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterAccountDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}