package tim22.upp.LiteralnoUdruzenje.dto;

public class ReaderDTO {

    private String firstname;

    private String lastname;

    private String city;

    private String country;

    private String email;

    private String username;

    private String password;

    private boolean betaReader;

    public ReaderDTO(String firstname, String lastname, String city, String country, String email, String username, String password, boolean betaReader) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
        this.email = email;
        this.username = username;
        this.password = password;
        this.betaReader = betaReader;
    }

    public ReaderDTO() { }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBetaReader() {
        return betaReader;
    }

    public void setBetaReader(boolean betaReader) {
        this.betaReader = betaReader;
    }
}
