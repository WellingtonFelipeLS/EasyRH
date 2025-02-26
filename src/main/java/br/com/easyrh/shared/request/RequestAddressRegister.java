package br.com.easyrh.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestAddressRegister(
        @NotBlank
        String publicArea,

        @NotBlank
        @Pattern(regexp = "\\d")
        String number,

        String complement,

        @NotBlank
        String neighborhood,

        @NotBlank
        String city,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep) {
}
