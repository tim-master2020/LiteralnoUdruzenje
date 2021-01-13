package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;


public interface IEmailService {

    public void sendCustomerEmail(User reader,String mailText);
    public void sendEditorEmailForReview(User user,String mailText);
    public void sendMail(User user,String mailText);
}
