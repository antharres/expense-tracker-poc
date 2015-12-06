package pl.maciejczekp.expense.tracker.model;

import javax.persistence.*;

@Entity
public class AppUser {
    @Id
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Account account;

    private AppUser() {
    }

    private AppUser(String email, String password, AppUserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static AppUser createUser(String email, String password, AppUserRole role) {
        return new AppUser(email, password, role);
    }

    public void createAccount(){
        this.account = Account.createAccount(this);
    }

    // ---

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AppUserRole getRole() {
        return role;
    }

    public Account getAccount() {
        return account;
    }
}
