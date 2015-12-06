package pl.maciejczekp.expense.tracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.maciejczekp.expense.tracker.accounts.ShowAccountUseCase;
import pl.maciejczekp.expense.tracker.accounts.ShowAccountUseCaseHandler;
import pl.maciejczekp.expense.tracker.users.UserRepository;

@Configuration
public class AccountUseCasesConfiguration {

    @Bean
    public ShowAccountUseCase showAccountUseCase(UserRepository userRepository) {
        return new ShowAccountUseCaseHandler(userRepository);
    }
}
