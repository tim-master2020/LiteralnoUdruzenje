package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

@Service
public class SendWriterRestOfWorkEmail implements JavaDelegate {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Sending mail to writer about more work");
        User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
        final String message = "Hello " + writer.getUsername() + ",\n\nOur editor has requested that you upload rest of your book.Please" +
                " login in our app and proceed with uploading.You have a period of 7 days to upload your work." +
                "\n\n\neBook Team";
        emailService.sendMail(writer,message);
    }
}
