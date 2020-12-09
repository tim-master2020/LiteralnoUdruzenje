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
    public void saveReader(Reader reader) {
        readerRepository.save(reader);
    }
}
