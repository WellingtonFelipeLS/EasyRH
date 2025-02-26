package br.com.easyrh.domain.entities;

import br.com.easyrh.shared.request.RequestEnterpriseEdit;
import br.com.easyrh.shared.request.RequestEnterpriseRegister;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "enterprises")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String cnpj;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "enterprise")
    private List<User> users;

    public Enterprise() {}

    public Enterprise(String name, String cnpj, String phoneNumber, String email, Address address) {
        this.name = name;
        this.cnpj = cnpj;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Enterprise(RequestEnterpriseRegister request) {
        this.name = request.name();
        this.cnpj = request.cnpj();
        this.phoneNumber = request.phoneNumber();
        this.email = request.email();
        this.address = new Address(request.address());
    }

    public Enterprise updateFromEditRequest(RequestEnterpriseEdit request) {
        if (request.name() != null)
            this.name = request.name();
        if (request.phoneNumber() != null)
            this.phoneNumber = request.phoneNumber();
        if (request.email() != null)
            this.email = request.email();
        if (request.address() != null) {
            this.address.updateFromEditRequest(request.address());
        }

        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(this.users);
    }

    public Address getAddress() {
        return address;
    }

    public void addUser(User user) {
        if (this.users == null) {
            this.users = List.of(user);
        } else {
            this.users.add(user);
        }
    }
}
