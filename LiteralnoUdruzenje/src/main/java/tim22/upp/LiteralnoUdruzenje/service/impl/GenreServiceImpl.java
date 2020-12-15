package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.repository.GenreRepository;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements IGenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GenreDTO> findAllGenres() {
        return convertModelToDto(genreRepository.findAll());
    }

    public List<GenreDTO> convertModelToDto(List<Genre> genres)
    {
        return genres.stream().map(g -> modelMapper.map(g, GenreDTO.class)).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }
}