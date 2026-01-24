package com.hexarcano.dlrecord.maintainer.application.port.in.command;

public record UpdateMaintainerCommand(String username, String email, String password, Boolean isAdmin) {
}
