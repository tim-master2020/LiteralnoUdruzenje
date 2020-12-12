package tim22.upp.LiteralnoUdruzenje.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tim22.upp.LiteralnoUdruzenje.dto.BookDTO;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.dto.WriterDTO;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.util.List;

@Controller
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<BookDTO>> getAll() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-genre")
    public ResponseEntity<List<BookDTO>> getAllByGenre(GenreDTO genreDTO) {
        return new ResponseEntity(bookService.findAllByGenre(modelMapper.map(genreDTO, Genre.class)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-writer")
    public ResponseEntity<List<BookDTO>> getAllByGenre(WriterDTO writerDTO) {
        return new ResponseEntity(bookService.findAllByWriter(modelMapper.map(writerDTO, Writer.class)), HttpStatus.OK);
    }
}
