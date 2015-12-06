package pl.maciejczekp.expense.tracker.usecase.users;

public interface RegisterUserUseCase {
    void execute(RegisterUserRequest request) throws UserAlreadyExistsException;

    class RegisterUserRequest {
        private String username;
        private String password;

        private RegisterUserRequest() {
        }

        public RegisterUserRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
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
