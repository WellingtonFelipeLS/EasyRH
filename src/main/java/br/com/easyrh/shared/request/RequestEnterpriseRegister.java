package br.com.easyrh.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestEnterpriseRegister(
        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{14}")
        String cnpj,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String phoneNumber,

        @NotBlank
        @Email
        String email,

        @NotNull
        RequestAddressRegister address,

        @NotNull
        RequestAdminRegister admin) {
}
