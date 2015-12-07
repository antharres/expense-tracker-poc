package pl.maciejczekp.expense.tracker.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Account {

    @Id
    @Column(name = "user_id")
    private String id;

    @MapsId
    @OneToOne(mappedBy = "account")
    @JoinColumn(name = "user_id")
    private AppUser user;

    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "user_id")
    private List<AccountEntry> entries = new LinkedList<>();

    private Account() {
    }

    private Account(AppUser user) {
        this.user = user;
    }

    static Account createAccount(AppUser user) {
        return new Account(user);
    }

    public void addEntry(Date date, AccountEntry.AccountEntryType type, BigDecimal amount) {
        this.entries.add(AccountEntry.create(date, type, amount));
        if (AccountEntry.AccountEntryType.INCOME.equals(type)) {
            this.balance = this.balance.add(amount);
        } else {
            this.balance = this.balance.subtract(amount);
        }
    }

    // ---

    public BigDecimal getBalance() {
        return balance;
    }

    public List<AccountEntry> getEntries() {
        return entries;
    }

    AppUser getUser() {
        return user;
    }
}
