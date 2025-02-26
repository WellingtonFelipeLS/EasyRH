package br.com.easyrh.shared.response;

import br.com.easyrh.domain.entities.Enterprise;

public record ResponseEnterpriseRegisterAndEdit(
        String name,
        String cnpj,
        String phoneNumber,
        String email,
        ResponseAddressRegisterAndEdit address) {
    public ResponseEnterpriseRegisterAndEdit(Enterprise enterprise) {
        this(enterprise.getName(), enterprise.getCnpj(), enterprise.getPhoneNumber(), enterprise.getEmail(),
                new ResponseAddressRegisterAndEdit(enterprise.getAddress()));
    }
}

