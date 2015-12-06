package pl.maciejczekp.expense.tracker.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.maciejczekp.expense.tracker.model.AppUser;
import pl.maciejczekp.expense.tracker.model.AppUserRole;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional(rollbackOn = Exception.class)
public class RegisterUserUseCaseHandler implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCaseHandler(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(RegisterUserUseCase.RegisterUserRequest request) throws UserAlreadyExistsException {
        Optional<AppUser> existingUser = userRepository.findOneByEmail(request.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists", request.getUsername()));
        }

        AppUser user = AppUser.createUser(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                AppUserRole.USER
        );

        userRepository.saveAndFlush(user);
    }
}
