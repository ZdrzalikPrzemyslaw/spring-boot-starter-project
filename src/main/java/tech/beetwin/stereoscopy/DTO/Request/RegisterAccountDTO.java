package tech.beetwin.stereoscopy.DTO.Request;

import tech.beetwin.stereoscopy.controllers.AccountController;

/**
 * This class defines a POJO used as a DTO to transfer information required to create a new account.
 * Used as a param in {@link AccountController#registerAccount(RegisterAccountDTO)}
 */
public class RegisterAccountDTO {
    // TODO: 12/11/2022 Nałożyć wymagania na hasło, i ogólnie bean validation 
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
