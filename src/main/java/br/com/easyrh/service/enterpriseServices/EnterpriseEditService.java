package br.com.easyrh.service.enterpriseServices;

import br.com.easyrh.domain.entities.Enterprise;
import br.com.easyrh.domain.repositories.EnterpriseRepository;
import br.com.easyrh.shared.request.RequestEnterpriseEdit;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseEditService {

    private final EnterpriseRepository enterpriseRepository;

    EnterpriseEditService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    @Transactional
    public Enterprise execute(RequestEnterpriseEdit request) {
        return enterpriseRepository.findByCnpj(request.cnpj())
                .map(enterprise -> enterprise.updateFromEditRequest(request))
                .orElseThrow(EntityNotFoundException::new);
    }
}
