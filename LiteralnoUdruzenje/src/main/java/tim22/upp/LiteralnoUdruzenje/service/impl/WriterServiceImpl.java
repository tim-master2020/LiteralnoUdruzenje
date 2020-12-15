package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.repository.WriterRepository;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

@Service
public class WriterServiceImpl implements IWriterService {

    @Autowired
    private WriterRepository writerRepository;

    @Override
    public Writer findByEmail(String email) {
        return writerRepository.findWriterByEmail(email);
    }
}
