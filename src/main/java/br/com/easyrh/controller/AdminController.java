package br.com.easyrh.controller;

import br.com.easyrh.service.adminServices.RegisterHRService;
import br.com.easyrh.shared.request.RequestHRRegister;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final RegisterHRService registerHRService;

    AdminController(RegisterHRService registerHRService) {
        this.registerHRService = registerHRService;
    }

    @PostMapping("hr")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> registerHR(@RequestBody @Valid RequestHRRegister request) {
        registerHRService.execute(request);

        return ResponseEntity.ok().build();
    }
}
