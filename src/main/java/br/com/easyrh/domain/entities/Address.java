package br.com.easyrh.domain.entities;

import br.com.easyrh.shared.request.RequestAddressEdit;
import br.com.easyrh.shared.request.RequestAddressRegister;
import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String publicArea;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String cep;

    public Address() {
    }

    public Address(String publicArea, String number, String complement, String neighborhood, String city, String cep) {
        this.publicArea = publicArea;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.cep = cep;
    }

    public Address(RequestAddressRegister request) {
        this.publicArea = request.publicArea();
        this.number = request.number();
        this.complement = request.complement();
        this.neighborhood = request.neighborhood();
        this.city = request.city();
        this.cep = request.cep();
    }

    public void updateFromEditRequest(RequestAddressEdit request) {
        if (request.publicArea() != null)
            this.publicArea = request.publicArea();
        if (request.number() != null)
            this.number = request.number();
        if (request.complement() != null)
            this.complement = request.complement();
        if (request.neighborhood() != null)
            this.neighborhood = request.neighborhood();
        if (request.city() != null)
            this.city = request.city();
        if (request.cep() != null)
            this.cep = request.cep();
    }

    public String getPublicArea() {
        return publicArea;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getCep() {
        return cep;
    }
}
