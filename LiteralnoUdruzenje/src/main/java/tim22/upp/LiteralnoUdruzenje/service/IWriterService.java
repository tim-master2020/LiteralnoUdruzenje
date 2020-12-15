package tim22.upp.LiteralnoUdruzenje.service;

import tim22.upp.LiteralnoUdruzenje.model.Writer;

public interface IWriterService {
    Writer findByEmail(String email);
}
