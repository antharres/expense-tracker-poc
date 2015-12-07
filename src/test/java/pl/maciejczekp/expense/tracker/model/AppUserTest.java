package pl.maciejczekp.expense.tracker.model;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class AppUserTest {

    @Test
    public void shouldSetRelationshipBetweenUserAndAccountWhenCreatingAccount() throws Exception {
        // Given
        AppUser user = AppUser.createUser("email", "password", AppUserRole.USER);
        // When
        user.createAccount();

        // Then
        Assert.assertThat(user.getAccount().getUser(), Matchers.is(user));
        Assert.assertThat(user.getAccount().getBalance(), Matchers.equalTo(BigDecimal.ZERO));
    }
}