package br.com.easyrh.domain.validation.userValidation;

import br.com.easyrh.domain.repositories.UserRepository;
import br.com.easyrh.infrastructure.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class DoubleUserRegisterValidation implements IUserRegisterValidation {
    private final UserRepository userRepository;

    DoubleUserRegisterValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ValidationException("Username already registered");
        }

    }
}
