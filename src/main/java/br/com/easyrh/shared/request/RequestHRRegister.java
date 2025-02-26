package br.com.easyrh.shared.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RequestHRRegister(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        @Pattern(regexp = "\\d{14}")
        String cnpj) {
}
