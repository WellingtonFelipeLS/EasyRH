package br.com.easyrh.domain.validation.enterpriseValidation;

import br.com.easyrh.shared.request.RequestEnterpriseRegister;

public interface IEnterpriseRegisterValidator {
    public void validate(RequestEnterpriseRegister dto);
}
