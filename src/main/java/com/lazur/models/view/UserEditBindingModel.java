package com.lazur.models.view;

import com.lazur.validations.IsPasswordsMatching;
import org.hibernate.validator.constraints.NotBlank;

@IsPasswordsMatching
public class UserEditBindingModel {

    @NotBlank
    private String username;

    private String password;

    private String confirmPassword;

    @NotBlank
    private String role;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
