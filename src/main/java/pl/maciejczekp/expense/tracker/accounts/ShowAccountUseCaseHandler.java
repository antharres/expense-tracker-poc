package pl.maciejczekp.expense.tracker.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.maciejczekp.expense.tracker.model.Account;
import pl.maciejczekp.expense.tracker.model.AppUser;
import pl.maciejczekp.expense.tracker.users.UserRepository;

import javax.transaction.Transactional;

@Component
@Transactional(rollbackOn = Exception.class)
public class ShowAccountUseCaseHandler implements ShowAccountUseCase {

    private final UserRepository userRepository;

    @Autowired
    public ShowAccountUseCaseHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Account execute(ShowAccountRequest request) {
        return userRepository.findOneByEmail(request.getUserEmail()).map(AppUser::getAccount).get();
    }
}
