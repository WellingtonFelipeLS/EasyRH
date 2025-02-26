package br.com.easyrh.shared.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestEnterpriseEdit(
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{14}")
        String cnpj,

        @Pattern(regexp = "\\d{11}")
        String phoneNumber,

        @Email
        String email,

        RequestAddressEdit address) {
}
