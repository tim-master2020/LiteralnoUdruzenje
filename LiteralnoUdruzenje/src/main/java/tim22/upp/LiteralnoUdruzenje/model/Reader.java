package tim22.upp.LiteralnoUdruzenje.model;

import org.camunda.bpm.engine.identity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.security.Timestamp;
import java.util.Date;


@Entity
public class Reader implements User,Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private boolean betaReader;

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
    private boolean isActiveAccount = false;

    @ManyToMany(mappedBy = "readers")
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(mappedBy = "betaReaders")
    private Set<Genre> betaGenres = new HashSet<>();

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

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

    public boolean isBetaReader() {
        return betaReader;
    }

    public void setBetaReader(boolean betaReader) {
        this.betaReader = betaReader;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Genre> getBetaGenres() {
        return betaGenres;
    }

    public void setBetaGenres(Set<Genre> betaGenres) {
        this.betaGenres = betaGenres;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActiveAccount() {
        return isActiveAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        isActiveAccount = activeAccount;

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Timestamp getLastPasswordResetDate() {
        return this.lastPasswordResetDate;

    }
}
