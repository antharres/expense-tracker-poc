package pl.maciejczekp.expense.tracker.usecase.users;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

public interface RegisterUserUseCase {
    void execute(RegisterUserRequest request) throws UserAlreadyExistsException;

    class RegisterUserRequest {
        @Email
        private String username;
        @Size(min = 0)
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
