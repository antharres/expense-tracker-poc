package pl.maciejczekp.expense.tracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.maciejczekp.expense.tracker.usecase.accounts.AddAccountEntryUseCase;
import pl.maciejczekp.expense.tracker.usecase.accounts.AddAccountEntryUseCaseHandler;
import pl.maciejczekp.expense.tracker.usecase.accounts.ShowAccountUseCase;
import pl.maciejczekp.expense.tracker.usecase.accounts.ShowAccountUseCaseHandler;
import pl.maciejczekp.expense.tracker.repositories.UserRepository;

@Configuration
public class AccountUseCasesConfiguration {

    @Bean
    public ShowAccountUseCase showAccountUseCase(UserRepository userRepository) {
        return new ShowAccountUseCaseHandler(userRepository);
    }

    @Bean
    public AddAccountEntryUseCase addAccountEntryUseCase(UserRepository userRepository) {
        return new AddAccountEntryUseCaseHandler(userRepository);
    }
}
