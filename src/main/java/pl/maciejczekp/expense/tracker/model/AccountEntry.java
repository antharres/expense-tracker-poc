package pl.maciejczekp.expense.tracker.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class AccountEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    private AccountEntryType type;

    private BigDecimal amount;

    private AccountEntry() {
    }

    private AccountEntry(Date date, AccountEntryType type, BigDecimal amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public static AccountEntry create(Date date, AccountEntryType type, BigDecimal amount) {
        return new AccountEntry(date, type, amount);
    }

    public enum AccountEntryType {
        EXPENSE, INCOME
    }

    // ---
    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public AccountEntryType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
