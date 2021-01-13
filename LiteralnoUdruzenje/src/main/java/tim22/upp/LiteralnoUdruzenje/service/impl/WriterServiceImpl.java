package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.repository.WriterRepository;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

@Service
public class WriterServiceImpl implements IWriterService {

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Writer saveWriter(Writer writer) {
        return (userService.findByUsername(writer.getUsername()) == null && userService.findByEmail(writer.getEmail()) == null) ? writerRepository.save(writer) : null;
    }

    @Override
    public Writer findByEmail(String email) {
        return writerRepository.findWriterByEmail(email);
    }

    @Override
    public Writer findByUsername(String username) {
        return writerRepository.findByUsername(username);
    }

    @Override
    public Writer updateWriter(Writer writer) {
        return writerRepository.save(writer);
    }
}
