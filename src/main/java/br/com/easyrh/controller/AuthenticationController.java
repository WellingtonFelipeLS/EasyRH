package br.com.easyrh.controller;

import br.com.easyrh.domain.entities.User;
import br.com.easyrh.infrastructure.security.TokenService;
import br.com.easyrh.shared.request.RequestLogin;
import br.com.easyrh.shared.response.ResponseLogin;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@SecurityRequirement(name = "bearer-key")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;


    AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<ResponseLogin> login(@RequestBody @Valid RequestLogin request) {
        var token = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new ResponseLogin(tokenJWT));
    }
}
