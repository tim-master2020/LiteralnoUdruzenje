package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;


public interface IEmailService {

    public void sendCustomerEmail(Reader reader,String mailText);
}
