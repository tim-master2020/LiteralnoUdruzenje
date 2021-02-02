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

    public Genre() {
    }

    public Genre(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Genre(String name)
    {
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

}