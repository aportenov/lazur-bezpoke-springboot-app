package com.lazur.models.view;

import com.lazur.validations.IsPasswordsMatching;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@IsPasswordsMatching
public class UserBindingModel {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String confirmPassword;

    @NotBlank(message = "Role must be chosen")
    private String role;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
