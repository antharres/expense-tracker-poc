package pl.maciejczekp.expense.tracker.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppUserTest {

    @Test
    public void equalsShouldReturnTrueForEqualUsers() throws Exception {
        AppUser u1 = AppUser.createUser("user1", "password", AppUserRole.USER);
    }
}