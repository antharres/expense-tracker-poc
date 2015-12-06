package pl.maciejczekp.expense.tracker.accounts;

import pl.maciejczekp.expense.tracker.model.Account;

public interface ShowAccountUseCase {
    Account execute(ShowAccountRequest request);

    class ShowAccountRequest {
        private final String userEmail;

        public ShowAccountRequest(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserEmail() {
            return userEmail;
        }
    }
}
