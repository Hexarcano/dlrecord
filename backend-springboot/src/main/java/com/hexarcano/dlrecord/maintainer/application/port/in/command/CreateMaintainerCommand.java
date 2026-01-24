package com.hexarcano.dlrecord.maintainer.application.port.in.command;

public record CreateMaintainerCommand(String username, String email, String password, Boolean isAdmin) {
}
