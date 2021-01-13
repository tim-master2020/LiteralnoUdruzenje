package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;

import java.util.List;

public interface IReaderService {

    Reader saveReader(Reader reader);
    Reader updateReader(Reader reader);
    Reader getOne(Long id);
    Reader findByUsername(String username);
    Reader findByEmail(String email);
    List<Reader> findBetaReaders();
}
