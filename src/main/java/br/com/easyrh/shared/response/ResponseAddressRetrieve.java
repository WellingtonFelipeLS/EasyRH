package br.com.easyrh.shared.response;

import br.com.easyrh.domain.entities.Address;

public record ResponseAddressRetrieve(
        String publicArea,
        String number,
        String complement,
        String neighborhood,
        String city,
        String cep) {
    public ResponseAddressRetrieve(Address address) {
        this(address.getPublicArea(), address.getNumber(), address.getComplement(), address.getNeighborhood(),
                address.getCity(), address.getCep());
    }
}
