package tim22.upp.LiteralnoUdruzenje.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.security.Timestamp;
import java.util.Date;


@Entity
public class Reader extends User implements Serializable{

    @ManyToMany(mappedBy = "betaReaders")
    private Set<Genre> betaGenres = new HashSet<>();

    @Column
    private boolean betaReader;

    public boolean isBetaReader() {
        return betaReader;
    }

    public void setBetaReader(boolean betaReader) {
        this.betaReader = betaReader;
    }

    public Set<Genre> getBetaGenres() {
        return betaGenres;
    }

    public void setBetaGenres(Set<Genre> betaGenres) {
        this.betaGenres = betaGenres;
    }
}
