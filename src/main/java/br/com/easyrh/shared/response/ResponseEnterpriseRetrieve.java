package br.com.easyrh.shared.response;

import br.com.easyrh.domain.entities.Enterprise;

public record ResponseEnterpriseRetrieve(
        String name,
        String cnpj,
        String phoneNumber,
        String email,
        ResponseAddressRetrieve address) {
    public ResponseEnterpriseRetrieve(Enterprise enterprise) {
        this(enterprise.getName(), enterprise.getCnpj(), enterprise.getPhoneNumber(), enterprise.getEmail(),
                new ResponseAddressRetrieve(enterprise.getAddress()));
    }
}
