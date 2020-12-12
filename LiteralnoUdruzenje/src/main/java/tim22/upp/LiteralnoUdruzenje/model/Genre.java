package tim22.upp.LiteralnoUdruzenje.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.glassfish.jersey.Beta;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<Book>();

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "genre_writers", joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private Set<Writer> authors = new HashSet<Writer>();

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "genre_betareader", joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "betareader_id", referencedColumnName = "id"))
    private Set<BetaReader> betaReaders = new HashSet<BetaReader>();

    public Genre() {
    }

    public Genre(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Writer> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Writer> authors) {
        this.authors = authors;
    }

    public Set<BetaReader> getBetaReaders() {
        return betaReaders;
    }

    public void setBetaReaders(Set<BetaReader> betaReaders) {
        this.betaReaders = betaReaders;
    }
}