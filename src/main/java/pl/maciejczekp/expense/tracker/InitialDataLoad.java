package pl.maciejczekp.expense.tracker;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.maciejczekp.expense.tracker.model.Account;
import pl.maciejczekp.expense.tracker.model.AccountEntry;
import pl.maciejczekp.expense.tracker.model.AppUser;
import pl.maciejczekp.expense.tracker.repositories.UserRepository;
import pl.maciejczekp.expense.tracker.usecase.users.RegisterUserUseCase;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Component
@Transactional
public class InitialDataLoad {
    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private UserRepository userRepository;

    public void load() throws Exception {
        String email = "foo@bar.pl";
        registerUserUseCase.execute(new RegisterUserUseCase.RegisterUserRequest(email, "qwe"));
        AppUser user = userRepository.findOne(email);
        Account account = user.getAccount();
        account.addEntry(new Date(), AccountEntry.AccountEntryType.INCOME, new BigDecimal(100));
        account.addEntry(new Date(), AccountEntry.AccountEntryType.EXPENSE, new BigDecimal(10));
    }
}
