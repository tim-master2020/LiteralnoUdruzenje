package tim22.upp.LiteralnoUdruzenje.dto;

import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class WriterDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String password;

    private String username;

    private String email;

    private String city;

    private String country;

    private boolean isActiveAccount = false;

    private Set<BookDTO> books = new HashSet<BookDTO>();

    private String role = "WRITER";

    public WriterDTO(){}

    public WriterDTO(Long id, String firstname, String lastname, String password, String username, String email, String city, String country, boolean isActiveAccount, Set<BookDTO> books) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.username = username;
        this.email = email;
        this.city = city;
        this.country = country;
        this.isActiveAccount = isActiveAccount;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDTO> books) {
        this.books = books;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
