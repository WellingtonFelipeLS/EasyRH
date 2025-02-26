package br.com.easyrh.controller.enterpriseController;

import br.com.easyrh.domain.entities.Address;
import br.com.easyrh.domain.entities.Enterprise;
import br.com.easyrh.service.enterpriseServices.EnterpriseRetrieveService;
import br.com.easyrh.shared.response.ResponseEnterpriseRetrieve;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class EnterpriseRetrieveControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<String> retrieveRequestDTO;

    @Autowired
    private JacksonTester<ResponseEnterpriseRetrieve> retrieveResponseDTO;

    @MockBean
    private EnterpriseRetrieveService enterpriseRetrieveService;

    @Test
    @WithMockUser
    @DisplayName("Should return 200 response code when everything happened as expected")
    void retrieve1() throws Exception {
        var request = "00000000000000";
        var enterprise = this.getEnterprise();
        var expectedResponse = new ResponseEnterpriseRetrieve(enterprise);

        when(enterpriseRetrieveService.execute(any())).thenReturn(enterprise);

        var response = mvc.perform(get("/enterprise/{cnpj}", request))
                .andReturn().getResponse();

        var expectedJson = retrieveResponseDTO.write(expectedResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @WithMockUser
    @DisplayName("Should return 404 response code when cnpj is empty")
    void retrieve2() throws Exception {
        var request = "";

        var response = mvc.perform(get("/enterprise/{cnpj}", request))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Should return 404 response code when cnpj is not registered")
    void retrieve3() throws Exception {
        var request = "00000000000001";

        when(enterpriseRetrieveService.execute(any())).thenThrow(new EntityNotFoundException("Not Found"));

        var response = mvc.perform(get("/enterprise/{cnpj}", request))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private Enterprise getEnterprise() {
        var address = new Address("test", "00", "", "test", "test", "00000000");
        return new Enterprise("enterprise", "00000000000000", "99999999999", "teste@email.com", address);
    }
}
