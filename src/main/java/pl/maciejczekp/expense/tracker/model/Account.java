package pl.maciejczekp.expense.tracker.model;

import javax.persistence.*;
import java.math.BigDecimal;

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

    private Account() {
    }

    private Account(AppUser user) {
        this.user = user;
    }

    static Account createAccount(AppUser user) {
        return new Account(user);
    }

    // ---

    public BigDecimal getBalance() {
        return balance;
    }

}
