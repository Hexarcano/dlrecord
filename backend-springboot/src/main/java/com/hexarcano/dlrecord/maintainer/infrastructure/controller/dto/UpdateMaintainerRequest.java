package com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto;

public record UpdateMaintainerRequest(String username, String email, String password, Boolean isAdmin) {
}
