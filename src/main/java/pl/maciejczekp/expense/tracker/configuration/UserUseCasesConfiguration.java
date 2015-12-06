package pl.maciejczekp.expense.tracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.maciejczekp.expense.tracker.usecase.users.RegisterUserUseCase;
import pl.maciejczekp.expense.tracker.usecase.users.RegisterUserUseCaseHandler;
import pl.maciejczekp.expense.tracker.repositories.UserRepository;

@Configuration
public class UserUseCasesConfiguration {

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCaseHandler(userRepository, passwordEncoder);
    }
}
