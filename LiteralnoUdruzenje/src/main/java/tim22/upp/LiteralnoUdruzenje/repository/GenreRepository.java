package tim22.upp.LiteralnoUdruzenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Reader;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findAll();
    Genre findByName(String name);
    Genre findById(long id);
}
