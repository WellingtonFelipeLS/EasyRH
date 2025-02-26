package br.com.easyrh.service.adminServices;

import br.com.easyrh.domain.entities.Enterprise;
import br.com.easyrh.domain.entities.User;
import br.com.easyrh.domain.repositories.UserRepository;
import br.com.easyrh.domain.validation.userValidation.IUserRegisterValidation;
import br.com.easyrh.shared.request.RequestAdminRegister;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterAdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<IUserRegisterValidation> registerValidation;

    RegisterAdminService(UserRepository userRepository, PasswordEncoder passwordEncoder, List<IUserRegisterValidation> registerValidation) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.registerValidation = registerValidation;
    }

    @Transactional
    public void registerAdminService(RequestAdminRegister request, Enterprise enterprise) {
        registerValidation.forEach(v -> v.validate(request.username()));

        var admin = new User(request.username(), passwordEncoder.encode(request.password()), "ROLE_ADMIN", enterprise);

        userRepository.save(admin);
    }

}
