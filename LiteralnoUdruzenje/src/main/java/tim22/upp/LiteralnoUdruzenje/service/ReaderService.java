package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;

public interface ReaderService {

    public Reader saveReader(Reader reader);
    public Reader getOne(Long id);
    public Reader findByUsername(String username);
}
