package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Override
    public void sendCustomerEmail(User user, String mailText) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("ebook.app.upp@gmail.com");
        mail.setSubject("Ebook");
        mail.setText(mailText);
        javaMailSender.send(mail);
    }

    @Override
    public void sendEditorEmailForReview(User user, String mailText) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("ebook.app.upp@gmail.com");
        mail.setSubject("Ebook");
        mail.setText(mailText);
        javaMailSender.send(mail);
    }

    public void sendMail(User user, String mailText) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("ebook.app.upp@gmail.com");
        mail.setSubject("Ebook");
        mail.setText(mailText);
        javaMailSender.send(mail);
    }
}
