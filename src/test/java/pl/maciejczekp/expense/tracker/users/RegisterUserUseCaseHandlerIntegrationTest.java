package pl.maciejczekp.expense.tracker.users;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.maciejczekp.expense.tracker.configuration.RepositoryConfiguration;
import pl.maciejczekp.expense.tracker.configuration.UserUserCasesConfiguration;
import pl.maciejczekp.expense.tracker.configuration.security.SecurityBeans;
import pl.maciejczekp.expense.tracker.model.AppUser;
import pl.maciejczekp.expense.tracker.model.AppUserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        RepositoryConfiguration.class,
        UserUserCasesConfiguration.class,
        SecurityBeans.class
})
@Transactional
public class RegisterUserUseCaseHandlerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldSaveRegisteredUserInDatabaseWithRoleUser() throws Exception {
        // Given
        String email = "email";
        String password = "password";
        RegisterUserUseCase.RegisterUserRequest request = new RegisterUserUseCase.RegisterUserRequest(
                email,
                password
        );

        // When
        registerUserUseCase.execute(request);

        // Then
        AppUser userFromDb = userRepository.findOne(email);
        Assert.assertThat(userFromDb, Matchers.notNullValue());
        Assert.assertThat(userFromDb.getEmail(), Matchers.equalTo(email));
        Assert.assertTrue(passwordEncoder.matches(password, userFromDb.getPassword()));
        Assert.assertThat(userFromDb.getRole(), Matchers.equalTo(AppUserRole.USER));
    }

    @Test(expected = RegisterUserUseCase.UserAlreadyExistsException.class)
    public void shouldThrowExceptionWhenCreatingDuplicateUser() throws Exception {
        // Given
        String email = "email";
        String password = "password";
        RegisterUserUseCase.RegisterUserRequest request = new RegisterUserUseCase.RegisterUserRequest(
                email,
                password
        );

        // When
        registerUserUseCase.execute(request);
        registerUserUseCase.execute(request);
    }
}