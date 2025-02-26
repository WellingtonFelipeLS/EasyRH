package br.com.easyrh.domain.repositories;

import br.com.easyrh.domain.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {
    Optional<Enterprise> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);
}
