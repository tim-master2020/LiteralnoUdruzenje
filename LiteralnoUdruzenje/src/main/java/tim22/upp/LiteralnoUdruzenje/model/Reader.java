package tim22.upp.LiteralnoUdruzenje.model;

import org.camunda.bpm.engine.identity.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Reader implements User,Serializable{

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


    @Id
    @Override
    public String getId() {
        return this.username;
    }

    @Override
    public void setId(String s) {
        this.username = s;
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
}
