package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

@Service
public class SendEmailForBadUpload implements JavaDelegate {
    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
        final String message = "Hello " + writer.getUsername() + ",\n\nYour book is not uploaded. Please try again.\n\n" + "\n\n\neBook Team";
        emailService.sendMail(writer,message);
    }
}
