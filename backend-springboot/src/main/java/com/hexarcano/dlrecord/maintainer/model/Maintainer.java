package com.hexarcano.dlrecord.maintainer.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Maintainer {
    private String uuid;
    private String username;
    private String email;
    private String password;
    private Boolean isAdmin = false;

    public Maintainer(String uuid, String username, String email, String password, Boolean isAdmin) {
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = (isAdmin != null) ? isAdmin : false;
    }

    public void changeUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public void changePassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void changeEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void changeIsAdmin(Boolean isAdmin) {
        if (isAdmin == null) {
            return;
        }

        this.isAdmin = isAdmin;
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            throw new IllegalArgumentException("Email is not valid.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
    }
}
