package pl.maciejczekp.expense.tracker.usecase.accounts;

import pl.maciejczekp.expense.tracker.model.AccountEntry;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public interface AddAccountEntryUseCase {
    void execute(AddAccountEntryRequest request);

    class AddAccountEntryRequest {
        private String userEmail;
        @NotNull
        private Date date;
        @NotNull
        private AccountEntry.AccountEntryType type;
        @Min(0)
        private BigDecimal amount;

        private AddAccountEntryRequest() {
        }

        public AddAccountEntryRequest(Date date, AccountEntry.AccountEntryType type, BigDecimal amount) {
            this.date = date;
            this.type = type;
            this.amount = amount;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public Date getDate() {
            return date;
        }

        public AccountEntry.AccountEntryType getType() {
            return type;
        }

        public BigDecimal getAmount() {
            return amount;
        }
    }
}
