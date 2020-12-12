package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.BookDTO;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.helper.ModelMapperBean;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.repository.BookRepository;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<BookDTO> findAll() {
        return convertFromModelToDto(bookRepository.findAll());
    }

    public List<BookDTO> convertFromModelToDto(List<Book> books) {
        return books.stream().map(b -> modelMapper.map(b, BookDTO.class)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Book findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    public List<Book> findAllByGenre(Genre genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    public List<Book> findAllByWriter(Writer writer) {
        return bookRepository.findBooskByWriter(writer);
    }
}
