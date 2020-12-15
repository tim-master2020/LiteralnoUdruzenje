package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.repository.ReaderRepository;
import tim22.upp.LiteralnoUdruzenje.service.ReaderService;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public Reader saveReader(Reader reader) {
        if (readerRepository.findByUsername(reader.getUsername()) == null) {
            return  readerRepository.save(reader);
        }
        return null;
    }

    @Override
    public Reader getOne(Long id) {
       return readerRepository.getOne(id);
    }

    @Override
    public Reader findByUsername(String username) {
        return readerRepository.findByUsername(username);
    }

}
