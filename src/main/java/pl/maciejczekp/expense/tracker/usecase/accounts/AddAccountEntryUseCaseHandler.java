package pl.maciejczekp.expense.tracker.usecase.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.maciejczekp.expense.tracker.model.Account;
import pl.maciejczekp.expense.tracker.model.AppUser;
import pl.maciejczekp.expense.tracker.repositories.UserRepository;

import javax.transaction.Transactional;

@Component
@Transactional(rollbackOn = Exception.class)
public class AddAccountEntryUseCaseHandler implements AddAccountEntryUseCase {

    private final UserRepository userRepository;

    @Autowired
    public AddAccountEntryUseCaseHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(AddAccountEntryRequest request) {
        Account account = userRepository.findOneByEmail(request.getUserEmail()).map(AppUser::getAccount).get();

        account.addEntry(request.getDate(), request.getType(), request.getAmount());
    }
}
