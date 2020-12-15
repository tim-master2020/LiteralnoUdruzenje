package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;

public interface IReaderService {

    Reader saveReader(Reader reader);
    Reader getOne(Long id);
    Reader findByUsername(String username);
    Reader findByEmail(String email);
}
