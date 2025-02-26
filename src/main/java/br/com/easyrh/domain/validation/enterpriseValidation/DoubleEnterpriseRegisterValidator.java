package br.com.easyrh.domain.validation.enterpriseValidation;

import br.com.easyrh.domain.repositories.EnterpriseRepository;
import br.com.easyrh.infrastructure.exception.ValidationException;
import br.com.easyrh.shared.request.RequestEnterpriseRegister;
import org.springframework.stereotype.Component;

@Component
public class DoubleEnterpriseRegisterValidator implements IEnterpriseRegisterValidator {

    public final EnterpriseRepository enterpriseRepository;

    public DoubleEnterpriseRegisterValidator(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    @Override
    public void validate(RequestEnterpriseRegister dto) {
        if (enterpriseRepository.existsByCnpj(dto.cnpj())) {
            throw new ValidationException("CNPJ already registered");
        }
    }
}
