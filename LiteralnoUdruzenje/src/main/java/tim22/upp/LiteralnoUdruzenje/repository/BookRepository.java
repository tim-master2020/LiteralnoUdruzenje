package tim22.upp.LiteralnoUdruzenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByGenre(Genre genre);
    Book findBookByWriter(Writer writer);
    List<Book> findAll();
    Book findBookByName(String name);
}
