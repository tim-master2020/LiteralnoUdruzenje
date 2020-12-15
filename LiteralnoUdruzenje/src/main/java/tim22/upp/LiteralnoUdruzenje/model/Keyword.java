package tim22.upp.LiteralnoUdruzenje.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<Book>();

    public Keyword() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
