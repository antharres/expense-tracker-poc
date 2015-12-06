package pl.maciejczekp.expense.tracker.users;

public interface RegisterUserUseCase {
    void execute(RegisterUserRequest request) throws UserAlreadyExistsException;

    class RegisterUserRequest {
        private final String email;
        private final String password;

        public RegisterUserRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String s) {
            super(s);
        }
    }
}
