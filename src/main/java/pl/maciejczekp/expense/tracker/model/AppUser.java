package pl.maciejczekp.expense.tracker.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class AppUser {
    @Id
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AppUserRole role;

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AppUserRole getRole() {
        return role;
    }
}
