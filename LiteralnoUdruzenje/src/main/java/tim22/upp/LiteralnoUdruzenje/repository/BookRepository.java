package tim22.upp.LiteralnoUdruzenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByGenre(Genre genre);
    //List<Book> findBookBy(Writer writer);
    List<Book> findAll();
    Book findBookByName(String name);
    Book save(Book book);
    Optional<Book> findById(Long id);
    void removeById(Long id);
}
