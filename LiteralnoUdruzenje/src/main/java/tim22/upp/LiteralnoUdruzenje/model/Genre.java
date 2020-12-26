package tim22.upp.LiteralnoUdruzenje.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.glassfish.jersey.Beta;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<Book>();

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)

    @JoinTable(name = "genre_users", joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    /*@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "genre_betareader", joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "betareader_id", referencedColumnName = "id"))
    private Set<BetaReader> betaReaders = new HashSet<BetaReader>();*/

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "betaReader_genres", joinColumns = @JoinColumn(name = "reader_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private Set<Reader> betaReaders = new HashSet<>();


    public Genre() {
    }

    public Genre(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Genre(String name)
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

    public Set<User> getUser() {
        return users;
    }

    public void setUser(Set<User> user) {
        this.users = user;
    }

    /*public Set<BetaReader> getBetaReaders() {
        return betaReaders;
    }

    public void setBetaReaders(Set<BetaReader> betaReaders) {
        this.betaReaders = betaReaders;
    }*/

    public Set<Reader> getBetaReaders() {
        return betaReaders;
    }

    public void setBetaReaders(Set<Reader> betaReaders) {
        this.betaReaders = betaReaders;
    }
}