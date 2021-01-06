package tim22.upp.LiteralnoUdruzenje.service;


import tim22.upp.LiteralnoUdruzenje.dto.BookDTO;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;

import java.util.List;

public interface IBookService {

    List<BookDTO> findAll();
    List<BookDTO> convertFromModelToDto(List<Book> books);
    Book findBookByName(String name);
    List<Book> findAllByGenre(Genre genre);
    //List<Book> findAllByWriter(Writer writer);
    boolean saveBook(Book book);
}
