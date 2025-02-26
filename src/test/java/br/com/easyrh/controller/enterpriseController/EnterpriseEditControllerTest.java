package br.com.easyrh.controller.enterpriseController;

import br.com.easyrh.domain.entities.Address;
import br.com.easyrh.domain.entities.Enterprise;
import br.com.easyrh.service.enterpriseServices.EnterpriseEditService;
import br.com.easyrh.shared.request.RequestEnterpriseEdit;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class EnterpriseEditControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<RequestEnterpriseEdit> editRequestDTO;

    @Autowired
    private JacksonTester<ResponseEnterpriseRegisterAndEdit> editResponseDTO;

    @MockBean
    private EnterpriseEditService enterpriseEditService;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("Should return 201 response code when everything happened as expected")
    void edit1() throws Exception {
        // Enterprise("enterprise", "00000000000000", "99999999999", "teste@email.com", someAddress) in the database
        var request = new RequestEnterpriseEdit("enterprise2", "00000000000000", null, null, null);
        var expectedEnterprise = getEnterprise();
        var expectedResponse = new ResponseEnterpriseRegisterAndEdit(expectedEnterprise);

        when(enterpriseEditService.execute(any())).thenReturn(expectedEnterprise);

        var response = mvc.perform(put("/enterprise").contentType(MediaType.APPLICATION_JSON).content(editRequestDTO.write(request).getJson()))
                .andReturn().getResponse();

        var expectedJson = editResponseDTO.write(expectedResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("Should return 400 response code when cnpj is blank")
    void edit2() throws Exception {
        // Enterprise("enterprise", "00000000000000", "99999999999", "teste@email.com", someAddress) in the database
        var request = new RequestEnterpriseEdit("enterprise2", null, null, null, null);

        var response = mvc.perform(put("/enterprise").contentType(MediaType.APPLICATION_JSON).content(editRequestDTO.write(request).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    private Enterprise getEnterprise() {
        var address = new Address("test", "00", "", "test", "test", "00000000");
        return new Enterprise("enterprise2", "00000000000000", "99999999999", "teste@email.com", address);
    }
}