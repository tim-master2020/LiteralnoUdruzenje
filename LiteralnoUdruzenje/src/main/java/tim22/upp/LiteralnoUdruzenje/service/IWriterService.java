package tim22.upp.LiteralnoUdruzenje.service;
import tim22.upp.LiteralnoUdruzenje.model.Writer;

public interface IWriterService {
    Writer saveWriter(Writer writer);
    Writer findByEmail(String email);
    Writer findByUsername(String username);
    Writer updateWriter(Writer writer);
}
