package tim22.upp.LiteralnoUdruzenje.service;


import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tim22.upp.LiteralnoUdruzenje.dto.BookDTO;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;

import java.io.IOException;
import java.util.List;

public interface IBookService {

    List<BookDTO> findAll();
    List<BookDTO> convertFromModelToDto(List<Book> books);
    Book findBookByName(String name);
    List<Book> findAllByGenre(Genre genre);
    //List<Book> findAllByWriter(Writer writer);
    List<String> savePdf(List<String> filesNames, String username);
    Book save(Book book);
    StreamingResponseBody downloadPDF(String name) throws IOException;
}
