package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.repository.ReaderRepository;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderServiceImpl implements IReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Reader saveReader(Reader reader) {
        if (userService.findByUsername(reader.getUsername()) == null && userService.findByEmail(reader.getEmail()) == null) {
            return  readerRepository.save(reader);
        }
        return null;
    }

    @Override
    public Reader updateReader(Reader reader) {
        return readerRepository.save(reader);
    }

    @Override
    public Reader getOne(Long id) {
       return readerRepository.getOne(id);
    }

    @Override
    public Reader findByUsername(String username) {
        return readerRepository.findByUsername(username);
    }

    @Override
    public Reader findByEmail(String email) {
        return readerRepository.findByEmail(email);
    }

    @Override
    public List<Reader> findBetaReaders() {
        List<Reader> readers = readerRepository.findAll();
        List<Reader> betaReaders = readers.stream().filter(Reader::isBetaReader).collect(Collectors.toList());
        return betaReaders;
    }

}
