package br.com.easyrh.shared.request;

import jakarta.validation.constraints.NotBlank;

public record RequestLogin(
        @NotBlank
        String username,

        @NotBlank
        String password) {
}
