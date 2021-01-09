package tim22.upp.LiteralnoUdruzenje.model;

import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Writer extends User implements Serializable {

    @Column
    private boolean isVerified;

    @ManyToMany(mappedBy = "writers")
    private Set<Book> books = new HashSet<Book>();

    public Writer() {}

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

}