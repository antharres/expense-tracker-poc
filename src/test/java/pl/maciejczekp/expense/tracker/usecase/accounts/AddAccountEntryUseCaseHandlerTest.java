package pl.maciejczekp.expense.tracker.usecase.accounts;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.maciejczekp.expense.tracker.configuration.AccountUseCasesConfiguration;
import pl.maciejczekp.expense.tracker.configuration.RepositoryConfiguration;
import pl.maciejczekp.expense.tracker.configuration.UserUseCasesConfiguration;
import pl.maciejczekp.expense.tracker.configuration.security.SecurityBeans;
import pl.maciejczekp.expense.tracker.model.Account;
import pl.maciejczekp.expense.tracker.model.AccountEntry;
import pl.maciejczekp.expense.tracker.repositories.UserRepository;
import pl.maciejczekp.expense.tracker.usecase.users.RegisterUserUseCase;

import java.math.BigDecimal;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        RepositoryConfiguration.class,
        UserUseCasesConfiguration.class,
        AccountUseCasesConfiguration.class,
        SecurityBeans.class
})
@Transactional
public class AddAccountEntryUseCaseHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private AddAccountEntryUseCase addAccountEntryUseCase;
    public static final String EMAIL = "email";

    @Before
    public void setUp() throws Exception {
        RegisterUserUseCase.RegisterUserRequest request = new RegisterUserUseCase.RegisterUserRequest(
                EMAIL,
                "password"
        );
        registerUserUseCase.execute(request);
    }

    @Test
    public void shouldAddExpenseEntryAndReduceAccountBalance() throws Exception {
        // Given
        Date date = new Date();
        AccountEntry.AccountEntryType type = AccountEntry.AccountEntryType.EXPENSE;
        BigDecimal amount = new BigDecimal(100);
        executeInternal(date, type, amount, amount.negate());
    }

    @Test
    public void shouldAddIncomeEntryAndIncreaseAccountBalance() throws Exception {
        // Given
        Date date = new Date();
        AccountEntry.AccountEntryType type = AccountEntry.AccountEntryType.INCOME;
        BigDecimal amount = new BigDecimal(100);
        executeInternal(date, type, amount, amount);
    }

    private void executeInternal(Date date, AccountEntry.AccountEntryType type, BigDecimal amount, BigDecimal expectedBalance) {
        String email = "email";

        AddAccountEntryUseCase.AddAccountEntryRequest request = new AddAccountEntryUseCase.AddAccountEntryRequest(
                date,
                type,
                amount
        );
        request.setUserEmail(email);

        // When
        addAccountEntryUseCase.execute(request);

        // Then
        Account account = userRepository.findOne(email).getAccount();
        Assert.assertThat(account.getBalance(), Matchers.is(expectedBalance));

        Assert.assertThat(account.getEntries(), Matchers.hasSize(1));
        AccountEntry accountEntry = account.getEntries().get(0);
        Assert.assertThat(accountEntry.getDate(), Matchers.equalTo(date));
        Assert.assertThat(accountEntry.getType(), Matchers.equalTo(type));
        Assert.assertThat(accountEntry.getAmount(), Matchers.equalTo(amount));
    }

}