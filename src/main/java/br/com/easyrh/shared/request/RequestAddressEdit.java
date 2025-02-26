package br.com.easyrh.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestAddressEdit(
        String publicArea,

        @Pattern(regexp = "\\d")
        String number,

        String complement,

        String neighborhood,

        String city,

        @Pattern(regexp = "\\d{8}")
        String cep) {
}
