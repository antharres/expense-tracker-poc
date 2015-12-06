package pl.maciejczekp.expense.tracker.usecase.users;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.maciejczekp.expense.tracker.repositories.UserRepository;
import pl.maciejczekp.expense.tracker.usecase.accounts.ShowAccountUseCase;
import pl.maciejczekp.expense.tracker.configuration.AccountUseCasesConfiguration;
import pl.maciejczekp.expense.tracker.configuration.RepositoryConfiguration;
import pl.maciejczekp.expense.tracker.configuration.UserUseCasesConfiguration;
import pl.maciejczekp.expense.tracker.configuration.security.SecurityBeans;
import pl.maciejczekp.expense.tracker.model.Account;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        RepositoryConfiguration.class,
        UserUseCasesConfiguration.class,
        AccountUseCasesConfiguration.class,
        SecurityBeans.class
})
@Transactional
public class ShowAccountUseCaseHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private ShowAccountUseCase showAccountUseCase;


    @Before
    public void setUp() throws Exception {
        String email = "email";
        String password = "password";
        RegisterUserUseCase.RegisterUserRequest request = new RegisterUserUseCase.RegisterUserRequest(
                email,
                password
        );
        registerUserUseCase.execute(request);
    }

    @Test
    public void shouldReturnUsersAccount() throws Exception {
        // Given
        String email = "email";
        ShowAccountUseCase.ShowAccountRequest request = new ShowAccountUseCase.ShowAccountRequest(
                email
        );

        // When
        Account account = showAccountUseCase.execute(request);

        // Then
        Assert.assertThat(userRepository.findOne(email).getAccount(), Matchers.is(account));
    }

}