package br.com.easyrh.controller;

import br.com.easyrh.service.enterpriseServices.EnterpriseEditService;
import br.com.easyrh.service.enterpriseServices.EnterpriseRegisterService;
import br.com.easyrh.service.enterpriseServices.EnterpriseRetrieveService;
import br.com.easyrh.shared.request.RequestEnterpriseEdit;
import br.com.easyrh.shared.request.RequestEnterpriseRegister;
import br.com.easyrh.shared.response.ResponseEnterpriseRegisterAndEdit;
import br.com.easyrh.shared.response.ResponseEnterpriseRetrieve;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("enterprise")
@SecurityRequirement(name = "bearer-key")
public class EnterpriseController {

    private final EnterpriseRegisterService enterpriseRegisterService;
    private final EnterpriseRetrieveService enterpriseRetrieveService;
    private final EnterpriseEditService enterpriseEditService;

    public EnterpriseController(EnterpriseRegisterService enterpriseRegisterService, EnterpriseRetrieveService enterpriseRetrieveService, EnterpriseEditService enterpriseEditService) {
        this.enterpriseRegisterService = enterpriseRegisterService;
        this.enterpriseRetrieveService = enterpriseRetrieveService;
        this.enterpriseEditService = enterpriseEditService;
    }

    @PostMapping
    public ResponseEntity<ResponseEnterpriseRegisterAndEdit> register(@RequestBody @Valid RequestEnterpriseRegister request, UriComponentsBuilder uriBuilder) {
        var enterprise = enterpriseRegisterService.execute(request);

        var uri = uriBuilder.path("/{cnpj}").buildAndExpand(request.cnpj()).toUri();

        var responseBody = new ResponseEnterpriseRegisterAndEdit(enterprise);

        return ResponseEntity.created(uri).body(responseBody);
    }

    @GetMapping("/{cnpj}")
    @Secured("ROLE_USER")
    public ResponseEntity<ResponseEnterpriseRetrieve> retrieve(@PathVariable("cnpj") String cnpj) {
        var enterprise = enterpriseRetrieveService.execute(cnpj);

        var responseBody = new ResponseEnterpriseRetrieve(enterprise);

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    // @PreAuthorize(tokenService.getEnterpriseClaim())
    public ResponseEntity<ResponseEnterpriseRegisterAndEdit> edit(@RequestBody @Valid RequestEnterpriseEdit request) {
        var responseBody = new ResponseEnterpriseRegisterAndEdit(enterpriseEditService.execute(request));
        return ResponseEntity.ok(responseBody);
    }
}
