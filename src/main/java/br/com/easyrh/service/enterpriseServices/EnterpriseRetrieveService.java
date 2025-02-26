package br.com.easyrh.service.enterpriseServices;

import br.com.easyrh.domain.entities.Enterprise;
import br.com.easyrh.domain.repositories.EnterpriseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseRetrieveService {

    private final EnterpriseRepository enterpriseRepository;

    EnterpriseRetrieveService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    public Enterprise execute(String cnpj) {
        return enterpriseRepository
                .findByCnpj(cnpj)
                .orElseThrow(EntityNotFoundException::new);
    }
}
