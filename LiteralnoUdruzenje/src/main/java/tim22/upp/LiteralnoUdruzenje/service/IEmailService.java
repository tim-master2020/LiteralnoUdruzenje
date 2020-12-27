package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.Writer;


public interface IEmailService {
    void sendEmail(User user, String mailText);
}
