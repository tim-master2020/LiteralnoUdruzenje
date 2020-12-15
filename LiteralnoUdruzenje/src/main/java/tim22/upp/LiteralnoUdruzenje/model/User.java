package tim22.upp.LiteralnoUdruzenje.model;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
public class User implements org.camunda.bpm.engine.identity.User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String password;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private boolean isActiveAccount = false;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public void setId(String s) {
        this.id = Long.parseLong(s);
    }

    @Override
    public String getFirstName() {
        return this.firstname;
    }

    @Override
    public void setFirstName(String s) {
        this.firstname = s;
    }

    @Override
    public void setLastName(String s) {
        this.lastname = s;
    }

    @Override
    public String getLastName() {
        return this.lastname;
    }

    @Override
    public void setEmail(String s) {
        this.email = s;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String s) {
        this.password = s;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Timestamp getLastPasswordResetDate() {
        return this.lastPasswordResetDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isActiveAccount() {
        return isActiveAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        isActiveAccount = activeAccount;
    }
}
