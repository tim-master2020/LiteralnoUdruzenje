package tim22.upp.LiteralnoUdruzenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;

import java.util.List;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Writer findWriterByName(String name);
    Writer findWriterByEmail(String email);
    Writer findWriterByGenre(Genre genre);
    List<Writer> findAll();
}
