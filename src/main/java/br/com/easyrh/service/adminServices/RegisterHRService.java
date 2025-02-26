package br.com.easyrh.service.adminServices;

import br.com.easyrh.domain.entities.User;
import br.com.easyrh.domain.repositories.UserRepository;
import br.com.easyrh.domain.validation.userValidation.IUserRegisterValidation;
import br.com.easyrh.service.enterpriseServices.EnterpriseRetrieveService;
import br.com.easyrh.shared.request.RequestHRRegister;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterHRService {
    private final UserRepository userRepository;
    private final EnterpriseRetrieveService enterpriseRetrieveService;
    private final PasswordEncoder passwordEncoder;
    private final List<IUserRegisterValidation> registerValidation;

    RegisterHRService(UserRepository userRepository, EnterpriseRetrieveService enterpriseRetrieveService, PasswordEncoder passwordEncoder, List<IUserRegisterValidation> registerValidation) {
        this.userRepository = userRepository;
        this.enterpriseRetrieveService = enterpriseRetrieveService;
        this.passwordEncoder = passwordEncoder;
        this.registerValidation = registerValidation;
    }

    @Transactional
    public void execute(RequestHRRegister request) {
        registerValidation.forEach(v -> v.validate(request.username()));

        var enterprise = enterpriseRetrieveService.execute(request.cnpj());

        var hr = new User(request.username(), passwordEncoder.encode(request.password()), "ROLE_USER", enterprise);

        userRepository.save(hr);
    }
}
