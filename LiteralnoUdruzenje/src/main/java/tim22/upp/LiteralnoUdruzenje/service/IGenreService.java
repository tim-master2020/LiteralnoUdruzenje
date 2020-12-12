package tim22.upp.LiteralnoUdruzenje.service;

import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.model.Genre;

import java.util.List;

public interface IGenreService {

    List<GenreDTO> findAllGenres();
    Genre findByName(String name);
}
