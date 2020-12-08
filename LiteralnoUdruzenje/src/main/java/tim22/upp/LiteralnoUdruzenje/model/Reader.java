package tim22.upp.LiteralnoUdruzenje.model;

import org.camunda.bpm.engine.identity.User;

import java.io.Serializable;

public class Reader implements User{

    private String city;

    private String country;

    private boolean betaReader;

    private String firstname;

    private String lastname;

    private String password;

    private String username;

    private String email;


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
