package tim22.upp.LiteralnoUdruzenje.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;

import java.util.List;

@Controller
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private IGenreService genreService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<GenreDTO>> getAll() {
        return new ResponseEntity<>(genreService.findAllGenres(), HttpStatus.OK);
    }
}
