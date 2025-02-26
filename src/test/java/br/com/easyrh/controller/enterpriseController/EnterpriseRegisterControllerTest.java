package br.com.easyrh.controller.enterpriseController;

import br.com.easyrh.domain.entities.Address;
import br.com.easyrh.domain.entities.Enterprise;
import br.com.easyrh.domain.entities.User;
import br.com.easyrh.infrastructure.exception.ValidationException;
import br.com.easyrh.service.enterpriseServices.EnterpriseRegisterService;
import br.com.easyrh.shared.request.RequestAddressRegister;
import br.com.easyrh.shared.request.RequestAdminRegister;
import br.com.easyrh.shared.request.RequestEnterpriseRegister;
import br.com.easyrh.shared.response.ResponseEnterpriseRegisterAndEdit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class EnterpriseRegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<RequestEnterpriseRegister> registerRequestDTO;

    @Autowired
    private JacksonTester<ResponseEnterpriseRegisterAndEdit> registerResponseDTO;

    @MockBean
    private EnterpriseRegisterService enterpriseRegisterService;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("Should return 201 response code when everything happened as expected")
    void register1() throws Exception {
        var enterprise = this.getEnterprise();
        var request = getRequestEnterpriseRegister(enterprise);
        var expectedResponse = new ResponseEnterpriseRegisterAndEdit(enterprise);

        when(enterpriseRegisterService.execute(any())).thenReturn(enterprise);

        var response = mvc.perform(post("/enterprise").contentType(MediaType.APPLICATION_JSON).content(registerRequestDTO.write(request).getJson()))
                .andReturn().getResponse();

        var expectedJson = registerResponseDTO.write(expectedResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("Should return 400 response code when the body is not sent")
    void register2() throws Exception {
        var response = mvc.perform(post("/enterprise"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("Should return 422 response code when business validation fails")
    void register3() throws Exception {
        var enterprise = this.getEnterprise();
        var request = getRequestEnterpriseRegister(enterprise);

        when(enterpriseRegisterService.execute(any())).thenThrow(new ValidationException("Validation exception"));

        var response = mvc.perform(post("/enterprise").contentType(MediaType.APPLICATION_JSON).content(registerRequestDTO.write(request).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    private Enterprise getEnterprise() {
        var address = new Address("test", "00", "", "test", "test", "00000000");
        return new Enterprise("enterprise", "00000000000000", "99999999999", "teste@email.com", address);
    }

    private RequestEnterpriseRegister getRequestEnterpriseRegister(Enterprise enterprise) {
        var address = enterprise.getAddress();
        var admin = new User("teste@dominio.com", "123", "ROLE_ADMIN", enterprise);
        var requestAddress = new RequestAddressRegister(address.getPublicArea(), address.getNumber(), address.getComplement(), address.getNeighborhood(), address.getCity(), address.getCep());
        var requestAdmin = new RequestAdminRegister(admin.getUsername(), admin.getPassword());

        return new RequestEnterpriseRegister(enterprise.getName(), enterprise.getCnpj(), enterprise.getPhoneNumber(), enterprise.getEmail(), requestAddress, requestAdmin);
    }
}
