package pl.maciejczekp.expense.tracker.model;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class AccountTest {

    private AppUser user;

    @Before
    public void setUp() throws Exception {
        user = AppUser.createUser("email", "password", AppUserRole.USER);
        user.createAccount();
    }

    @Test
    public void shouldReduceBalanceWhenAddingExpense() throws Exception {
        // Given
        Account account = user.getAccount();
        // When
        BigDecimal amount = new BigDecimal(10);
        account.addEntry(new Date(), AccountEntry.AccountEntryType.EXPENSE, amount);

        // Then
        Assert.assertThat(account.getBalance(), Matchers.equalTo(amount.negate()));
    }

    @Test
    public void shouldIncreaseBalanceWhenAddingExpense() throws Exception {
        // Given
        Account account = user.getAccount();
        // When
        BigDecimal amount = new BigDecimal(10);
        account.addEntry(new Date(), AccountEntry.AccountEntryType.INCOME, amount);

        // Then
        Assert.assertThat(account.getBalance(), Matchers.equalTo(amount));
    }
}