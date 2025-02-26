package br.com.easyrh.service.enterpriseServices;

import br.com.easyrh.domain.entities.Enterprise;
import br.com.easyrh.domain.repositories.EnterpriseRepository;
import br.com.easyrh.domain.validation.enterpriseValidation.IEnterpriseRegisterValidator;
import br.com.easyrh.service.adminServices.RegisterAdminService;
import br.com.easyrh.shared.request.RequestEnterpriseRegister;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseRegisterService {

    private final EnterpriseRepository enterpriseRepository;
    private final List<IEnterpriseRegisterValidator> registerValidators;
    private final RegisterAdminService registerAdminService;

    EnterpriseRegisterService(EnterpriseRepository enterpriseRepository, List<IEnterpriseRegisterValidator> registerValidators, RegisterAdminService registerAdminService) {
        this.enterpriseRepository = enterpriseRepository;
        this.registerValidators = registerValidators;
        this.registerAdminService = registerAdminService;
    }

    @Transactional
    public Enterprise execute(RequestEnterpriseRegister request) {
        registerValidators.forEach(v -> v.validate(request));

        var enterprise = new Enterprise(request);
        var savedEnterprise = enterpriseRepository.save(enterprise);

        registerAdminService.registerAdminService(request.admin(), savedEnterprise);

        return savedEnterprise;
    }


}
