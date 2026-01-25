package com.hexarcano.dlrecord.auth.application.port.in.command;

public record SignUpCommand(String username, String email, String password, Boolean isAdmin) {
}
