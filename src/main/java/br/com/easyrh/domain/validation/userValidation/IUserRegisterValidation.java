package br.com.easyrh.domain.validation.userValidation;

import br.com.easyrh.shared.request.RequestAdminRegister;

public interface IUserRegisterValidation {
    void validate(String username);
}
